/**
 * Author: Anvar Olimov
 * User:user
 * Date:6/24/2024
 * Time:3:04 PM
 */


package dental.patient_history.service;

import dental.client.entity.Client;
import dental.client.service.ClientServiceImpl;
import dental.epms.dto.EmployeeShortInfoDto;
import dental.epms.entity.Employees;
import dental.epms.service.EmployeeServiceImpl;
import dental.epms.service.JwtServiceImpl;
import dental.exception.AppointmentTimeNotAvailableException;
import dental.exception.ResourceNotFoundException;
import dental.patient_history.dto.*;
import dental.patient_history.entity.PatientHistoryEntity;
import dental.patient_history.repository.PatientRepository;
import dental.utils.DefaultResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
@Transactional
public class PatientHistoryServiceImpl implements PatientHistoryService {


    private final PatientRepository repository;
    private final EmployeeServiceImpl employeeService;
    private final ClientServiceImpl clientService;
    private final TeethServiceSImpl serviceS;
    private final JwtServiceImpl jwtService;

    @Override
    public String create(@NotNull PatientAddDto dto) {

        Employees doctor = employeeService.getByID(dto.getEmployeeId());
        Client client = clientService.getClientByID(dto.getClientId());

        boolean isTaken = false;

        PatientHistoryEntity patientHistoryEntity = new PatientHistoryEntity();
        patientHistoryEntity.setCreatedAt(LocalDateTime.now());
        patientHistoryEntity.setEmployees(doctor);
        patientHistoryEntity.setPaid(0);
        patientHistoryEntity.setTotal(0);
        patientHistoryEntity.setExpense(0);
        patientHistoryEntity.setIsPaid(false);
        patientHistoryEntity.setIsServiced(false);
        patientHistoryEntity.setDeleted(false);
        patientHistoryEntity.setClient(client);
        patientHistoryEntity.setAppointmentTime(dto.getAppointmentTime());
        patientHistoryEntity.setEndTime(dto.getEndTime());

        if (!patientHistoryEntity.getAppointmentTime().isAfter(patientHistoryEntity.getEndTime())
                && !patientHistoryEntity.getAppointmentTime().isEqual(patientHistoryEntity.getEndTime())) {
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
        } else {
            throw new AppointmentTimeNotAvailableException("Vaqtni noto`g`ri tanlagansiz");
        }

    }

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
    public List<PatientResponseDto> findAll() {
        List<PatientResponseDto> dto = new ArrayList<>(repository.findAll()
                .stream()
                .filter(PatientHistoryEntity::getActive)
                .map(this::toDto)
                .toList());
        dto.sort(Comparator.comparing(PatientResponseDto::getPatientLName));
        return dto;
    }

    public List<PatientResponseDto> findAllAfter() {
        return repository.findAll()
                .stream()
                .filter(PatientHistoryEntity::getActive)
                .filter(p -> !p.getAppointmentTime().toLocalDate().isBefore(LocalDate.now()))
                .map(this::toDto)
                .toList();
    }

    public List<PatientShortInfoDto> findAllShort() {
        List<PatientShortInfoDto> dto = new ArrayList<>(repository.findAll()
                .stream()
                .filter(PatientHistoryEntity::getActive)
                .map(this::toShortDto)
                .toList());
        dto.sort(Comparator.comparing(PatientShortInfoDto::getPatientLName));
        return dto;
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
                .filter(PatientHistoryEntity::getActive)
                .map(this::toDto)
                .toList();

    }

    @Override
    public List<PatientResponseDto> findByEmpIdIsServiced(Long id) {
        return repository.findByEmployeesId(id)
                .stream()
                .filter(entity -> !entity.getIsServiced())
                .filter(PatientHistoryEntity::getActive)
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<PatientResponseDto> findByEmpIdIsNotServiced(Long id) {
        return repository.findByEmployeesId(id)
                .stream()
                .filter(PatientHistoryEntity::getIsServiced)
                .filter(PatientHistoryEntity::getActive)
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<PatientResponseDto> findByClientId(Long id) {
        return repository.findByClient_Id(id)
                .stream()
                .filter(PatientHistoryEntity::getActive)
                .map(this::toDto)
                .toList();
    }

    @Override
    public PatientHistoryEntity getPatientById(Long id) {

        return repository.findById(id).orElse(null);
    }

    @Override
    public List<PatientResponseDto> findDebitPatient() {
        return repository.findAll()
                .stream()
                .filter(PatientHistoryEntity::getActive)
                .filter(debit->!debit.getIsPaid())
                .filter(PatientHistoryEntity::getIsServiced)
                .map(this::toDto)
                .toList();
    }

    @Override
    public PatientResponseDto findById(Long id) {
        Optional<PatientHistoryEntity> entityOptional = repository.findById(id);
        if (entityOptional.isEmpty() || !entityOptional.get().getActive()) {
            throw new ResourceNotFoundException("Bunday mijoz yo`q");
        }

        return toDto(entityOptional.get());

    }

    @Override
    public void save(PatientHistoryEntity entity) {
        repository.save(entity);
    }

    @Override
    public Integer countClientNotServiced() {
        List<PatientHistoryEntity> patientHistoryEntities = repository.findAll();
        Integer count = 0;
        for (PatientHistoryEntity entity : patientHistoryEntities) {
            if (entity.getIsServiced().equals(false) && entity.getDeleted().equals(false)){
                count++;
            }
        }
        return count;
    }

    public List<MonthlyIncomeExpenseDTO> getMonthlyTotalIncomeAndExpense() {
        LocalDateTime twelveMonthsAgo = LocalDateTime.now().minusMonths(12);
        List<Object[]> results = repository.findMonthlyTotalIncomeAndExpense(twelveMonthsAgo);

        List<MonthlyIncomeExpenseDTO> list = new ArrayList<>();
        for (Object[] row : results) {
            int year = (Integer) row[0];
            int month = (Integer) row[1];
            Long totalIncome = row[2] != null ? (Long) row[2] : 0L;
            Long totalExpense = row[3] != null ? (Long) row[3] : 0L;

            list.add(new MonthlyIncomeExpenseDTO(year, month, null, totalIncome, totalExpense));
        }

        return list;
    }

    public List<MonthlyCountDTO> getMonthlyAppointmentCount() {
        LocalDateTime twelveMonthsAgo = LocalDateTime.now().minusMonths(12);
        List<Object[]> results = repository.countAppointmentsByMonth(twelveMonthsAgo);

        List<MonthlyCountDTO> monthlyCounts = new ArrayList<>();
        for (Object[] row : results) {
            int year = (Integer) row[0];  // yil
            int month = (Integer) row[1]; // oy
            long count = (Long) row[2];   // uchrashuvlar soni

            MonthlyCountDTO dto = new MonthlyCountDTO(year, month, count);
            monthlyCounts.add(dto);
        }
        return monthlyCounts;
    }

    public List<MonthlyIncomeExpenseDTO> getMonthlyIncomeAndExpensePerEmployee() {
        LocalDateTime twelveMonthsAgo = LocalDateTime.now().minusMonths(12);
        List<Object[]> results = repository.findMonthlyIncomeAndExpensePerEmployee(twelveMonthsAgo);

        List<MonthlyIncomeExpenseDTO> monthlyList = new ArrayList<>();
        for (Object[] row : results) {
            int year = (Integer) row[0];        // yil
            int month = (Integer) row[1];       // oy
            Long employeeId = (Long) row[2];    // doctor ID
            Long totalIncome = (row[3] != null) ? (Long) row[3] : 0L;   // kirim
            Long totalExpense = (row[4] != null) ? (Long) row[4] : 0L;  // chiqim

            MonthlyIncomeExpenseDTO dto = new MonthlyIncomeExpenseDTO(year, month, employeeId, totalIncome, totalExpense);
            monthlyList.add(dto);
        }

        return monthlyList;
    }
    @Override
    public Integer countClientServiced() {
        List<PatientHistoryEntity> patientHistoryEntities = repository.findAll();
        Integer count = 0;
        for (PatientHistoryEntity entity : patientHistoryEntities) {
            if (entity.getIsServiced().equals(true) && entity.getDeleted().equals(false)){
                count++;
            }
        }
        return count;
    }

    @Override
    public EmployeeShortInfoDto findDoctorId(String token) {
        return jwtService.returnIdByToken(token);
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
    public Boolean hasClient(Client client) {
        boolean hasId = false;
        List<PatientHistoryEntity> optionalList = repository.findAll();
        for (PatientHistoryEntity patientHistory : optionalList) {
            if (patientHistory.getClient().equals(client)) {
                hasId = true;
                break;
            }
        }
        return hasId;
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

    @Override
    public void deleteWithEmployee(Long id) {
        List<PatientHistoryEntity> patientHistoryEntities = repository.findAll();
        for (PatientHistoryEntity entity : patientHistoryEntities){

            if(entity.getEmployees().getId().equals(id) && Boolean.TRUE.equals(entity.getDeleted())){
                entity.setDeleted(true);
            }
            if(entity.getEmployees().getId().equals(id)){
                entity.setDeleted(true);
            }
        }
    }

    @Override
    public void deleteWithClient(Long id) {
       List<PatientHistoryEntity> optionalList = repository.findAll();
       for (PatientHistoryEntity entity : optionalList){
           if (Objects.equals(entity.getClient().getId(), id)){
               entity.setDeleted(true);
           }
       }
    }

    @Override
    public String activeDelete(Long id) {
        Optional<PatientHistoryEntity> optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new ResourceNotFoundException("Bunday id mavjud emas");
        }
        PatientHistoryEntity entity = optional.get();
        entity.setActive(false);
        repository.save(entity);
        return "id" + " o`chirildi";
    }

    public PatientResponseDto toDtoService(PatientHistoryEntity entity) {

        if (entity == null) {
            return new PatientResponseDto();
        }
        PatientResponseDto responseDto = new PatientResponseDto();
        responseDto.setId(entity.getId());
        responseDto.setEmpId(entity.getEmployees().getId());
        responseDto.setClientId(entity.getClient().getId());
        responseDto.setEmpName(entity.getEmployees().getFirstName());
        responseDto.setEmpLName(entity.getEmployees().getLastName());
        responseDto.setPatientName(entity.getClient().getName());
        responseDto.setPatientLName(entity.getClient().getLastName());
        responseDto.setPhoneNumber(entity.getClient().getPhoneNumber());
        responseDto.setGender(entity.getClient().getGender());
        responseDto.setBirthDay(entity.getClient().getBirthday());
        responseDto.setIsServiced(entity.getIsServiced());
        responseDto.setTeethServiceEntities(getServiceListDto(entity));
        responseDto.setExpense(entity.getExpense());
        responseDto.setCreatedAt(entity.getCreatedAt());
        return responseDto;
    }

    public PatientResponseDto toDto(PatientHistoryEntity entity) {

        if (entity == null) {
            return new PatientResponseDto();
        }
        PatientResponseDto responseDto = new PatientResponseDto();
        responseDto.setId(entity.getId());
        responseDto.setEmpId(entity.getEmployees().getId());
        responseDto.setClientId(entity.getClient().getId());
        responseDto.setEmpName(entity.getEmployees().getFirstName());
        responseDto.setEmpLName(entity.getEmployees().getLastName());
        responseDto.setPatientName(entity.getClient().getName());
        responseDto.setPatientLName(entity.getClient().getLastName());
        responseDto.setPhoneNumber(entity.getClient().getPhoneNumber());
        responseDto.setGender(entity.getClient().getGender());
        responseDto.setBirthDay(entity.getClient().getBirthday());
        responseDto.setIsServiced(entity.getIsServiced());
        responseDto.setTeethServiceEntities(getServiceListDto(entity));
        responseDto.setExpense(entity.getExpense());
        responseDto.setCreatedAt(entity.getCreatedAt());
        responseDto.setAppointmentTime(entity.getAppointmentTime());
        responseDto.setEndTime(entity.getEndTime());
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
}

