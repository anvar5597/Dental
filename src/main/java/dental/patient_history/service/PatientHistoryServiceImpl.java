/**
 * Author: Anvar Olimov
 * User:user
 * Date:6/24/2024
 * Time:3:04 PM
 */


package dental.patient_history.service;

import dental.client.service.ClientServiceImpl;
import dental.client.entity.Client;
import dental.epms.entity.Employees;
import dental.epms.service.EmployeeServiceImpl;
import dental.epms.service.JwtServiceImpl;
import dental.exception.AppointmentTimeNotAvailableException;
import dental.exception.ResourceNotFoundException;
import dental.patient_history.dto.*;
import dental.patient_history.entity.PatientHistoryEntity;
import dental.patient_history.mapper.PatientHistoryMapper;
import dental.patient_history.repository.PatientHistoryRepository;
import dental.payment.dto.PaymentResponseDto;
import dental.utils.DefaultResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class PatientHistoryServiceImpl implements PatientHistoryService {


    private final PatientHistoryRepository repository;
    private final EmployeeServiceImpl employeeService;
    private final ClientServiceImpl clientService;
    private final PatientHistoryMapper mapper;
    private final TeethServiceSImpl serviceS;
    private final JwtServiceImpl jwtService;


    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public List<PatientResponseDto> findAll() {
        List<PatientResponseDto> dto = new ArrayList<>(repository.findAll()
                .stream()
                .map(this::toDto)
                .toList());
        dto.sort(Comparator.comparing(PatientResponseDto::getPatientLName));
        return dto;
    }

    public List<PatientShortInfoDto> findAllShort() {
        List<PatientShortInfoDto> dto = new ArrayList<>(repository.findAll()
                .stream()
                .map(this::toShortDto)
                .toList());
        dto.sort(Comparator.comparing(PatientShortInfoDto::getPatientLName));
        return dto;
    }


    public PatientResponseDto toDto(@NotNull PatientHistoryEntity entity) {
        PatientResponseDto responseDto = new PatientResponseDto();
        responseDto.setId(entity.getId());
        responseDto.setEmpName(entity.getEmployees().getFirstName());
        responseDto.setEmpLName(entity.getEmployees().getLastName());
        responseDto.setPatientName(entity.getClient().getName());
        responseDto.setPatientLName(entity.getClient().getLastName());
        responseDto.setPhoneNumber(entity.getClient().getPhoneNumber());
        responseDto.setIsServiced(entity.getIsServiced());
        responseDto.setGender(entity.getClient().getGender());
        responseDto.setBirthDay(entity.getClient().getBirthday());
        responseDto.setTeethServiceEntities(getServiceListDto(entity));
        responseDto.setCreatedAt(entity.getCreatedAt());
        return responseDto;
    }

    public PatientShortInfoDto toShortDto(@NotNull PatientHistoryEntity entity) {
        PatientShortInfoDto shortInfoDto = new PatientShortInfoDto();

        shortInfoDto.setId(entity.getId());
        shortInfoDto.setPatientName(entity.getClient().getName());
        shortInfoDto.setPatientLName(entity.getClient().getLastName());
        shortInfoDto.setBirthday(entity.getClient().getBirthday());
        shortInfoDto.setGender(entity.getClient().getGender());
        List<TServiceListDto> tServiceListDto = getServiceListDto(entity);
        shortInfoDto.setTeethServiceEntities(tServiceListDto);
        shortInfoDto.setIsServiced(entity.getIsServiced());
        return shortInfoDto;
    }

    @NotNull
    private static List<TServiceListDto> getServiceListDto(@NotNull PatientHistoryEntity entity) {
        List<TServiceListDto> tServiceListDto = new ArrayList<>();
        for (int i = 0; i < entity.getTeethServiceEntities().size(); i++) {
            TServiceListDto tServiceListDto1 = getServiceListDto(entity, i);
            tServiceListDto.add(tServiceListDto1);
        }
        return tServiceListDto;
    }

    @NotNull
    private static TServiceListDto getServiceListDto(@NotNull PatientHistoryEntity entity, int i) {
        TServiceListDto tServiceListDto1 = new TServiceListDto();
        tServiceListDto1.setTeethName(entity.getTeethServiceEntities().get(i).getTeethServiceKey().getTeeth().getTeethName());
        tServiceListDto1.setServiceName(entity.getTeethServiceEntities().get(i).getTeethServiceKey().getService().getServiceName());
        tServiceListDto1.setPrice(entity.getTeethServiceEntities().get(i).getTeethServiceKey().getService().getPrice());
        return tServiceListDto1;
    }


    @Override
    public List<PatientResponseDto> findByEmpId(Long id) {

        List<PatientHistoryEntity> patientHistoryEntities = repository.findByEmployeesId(id);
        return patientHistoryEntities.stream()
                .map(this::toDto)
                .toList();

    }

    @Override
    public PatientHistoryEntity getPatientById(Long id) {

        return repository.findById(id).orElse(null);
    }

    @Override
    public PatientResponseDto findById(Long id) {
        Optional<PatientHistoryEntity> entityOptional = repository.findById(id);
        if (entityOptional.isEmpty()) {
            throw new ResourceNotFoundException("Bunday mijoz yo`q");
        }

        return toDto(entityOptional.get());

    }

    @Override
    public void save(PatientHistoryEntity entity) {
        repository.save(entity);
    }

    @Override
    public Long findDoctorId(String token) {
        return jwtService.returnIdByToken(token);
    }

    @Override
    public String create(@NotNull PatientAddDto dto) {

        Employees doctor = employeeService.getByID(dto.getEmployeeId());
        Client client = clientService.getClientByID(dto.getClientId());

        boolean isTaken = false;

        PatientHistoryEntity patientHistoryEntity = new PatientHistoryEntity();
        patientHistoryEntity.setCreatedAt(LocalDate.now());
        patientHistoryEntity.setEmployees(doctor);
        patientHistoryEntity.setPaid(0);
        patientHistoryEntity.setTotal(0);
        patientHistoryEntity.setIsPaid(false);
        patientHistoryEntity.setIsServiced(false);
        patientHistoryEntity.setDeleted(false);
        patientHistoryEntity.setClient(client);
        patientHistoryEntity.setAppointmentTime(dto.getAppointmentTime());
        patientHistoryEntity.setEndTime(dto.getEndTime());

        if (patientHistoryEntity.getAppointmentTime().isAfter(patientHistoryEntity.getEndTime())
                || patientHistoryEntity.getAppointmentTime().isEqual(patientHistoryEntity.getEndTime())) {
            throw new AppointmentTimeNotAvailableException("Vaqtni noto`g`ri tanlagansiz");
        }

        List<PatientHistoryEntity> entities = repository.findAll().stream().toList();
        for (PatientHistoryEntity entity : entities) {
            if (entity.getDeleted().equals(false)
                    && (patientHistoryEntity.getEmployees().getId().equals(entity.getEmployees().getId())
                    || patientHistoryEntity.getClient().getId().equals(entity.getClient().getId()))
                    && ((patientHistoryEntity.getAppointmentTime().isEqual(entity.getAppointmentTime())
                    && patientHistoryEntity.getEndTime().isEqual(entity.getEndTime()))
                    || (patientHistoryEntity.getAppointmentTime().isBefore(entity.getAppointmentTime())
                    && patientHistoryEntity.getEndTime().isAfter(entity.getEndTime()))
                    || (patientHistoryEntity.getAppointmentTime().isBefore(entity.getAppointmentTime())
                    && patientHistoryEntity.getEndTime().isAfter(entity.getAppointmentTime()))
                    || (patientHistoryEntity.getAppointmentTime().isBefore(entity.getEndTime())
                    && patientHistoryEntity.getEndTime().isAfter(entity.getEndTime()))
                    || (patientHistoryEntity.getAppointmentTime().isAfter(entity.getAppointmentTime())
                    && patientHistoryEntity.getEndTime().isBefore(entity.getEndTime()))
                    || (patientHistoryEntity.getAppointmentTime().isEqual(entity.getAppointmentTime())
                    && patientHistoryEntity.getEndTime().isBefore(entity.getEndTime()))
                    || (patientHistoryEntity.getEndTime().isEqual(entity.getEndTime())
                    && patientHistoryEntity.getAppointmentTime().isAfter(entity.getAppointmentTime())))) {
                isTaken = true;
                break;
            }


        }
        if (isTaken) {
            throw new AppointmentTimeNotAvailableException("Bu tashrif allaqachon band qilingan");
        }


        repository.save(patientHistoryEntity);
        return "Tashrif yaratildi";
    }

    @Override
    public String serviced(Long id) {
        Optional<PatientHistoryEntity> entity = repository.findById(id);
        if (entity.isEmpty()) {
            throw new ResourceNotFoundException("Bunday id mavjud emas");
        }
        PatientHistoryEntity patientHistory = entity.get();
        patientHistory.setIsServiced(true);
        return "Hizmat bajarildi";
    }


    @Override
    public DefaultResponseDto addService(PatientServiceAddDto dto) {
        return serviceS.createTeethServiceForPatientHistory(dto);
    }


    @Override
    public String update(@NotNull PatientRequestDto dto, Long id) {

        Employees doctor = employeeService.getByID(dto.getEmployeeId());
        Client client = clientService.getClientByID(dto.getClientId());
        PatientHistoryEntity patientHistoryEntity = new PatientHistoryEntity();
        patientHistoryEntity.setId(id);
        patientHistoryEntity.setEmployees(doctor);
        patientHistoryEntity.setIsServiced(false);
        patientHistoryEntity.setClient(client);
        repository.save(patientHistoryEntity);
        return client.getName() + "ning tashrifi o`zgartirildi";

    }

    @Override
    public String delete(Long id) {
        Optional<PatientHistoryEntity> patientHistoryEntity = repository.findById(id);
        if (patientHistoryEntity.isEmpty()) {
            throw new ResourceNotFoundException("Bunday id mavjud emas");
        }

        patientHistoryEntity.get().setDeleted(true);
        return "id" + " raqamli tashrif o`chirildi";
    }
}

