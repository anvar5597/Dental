/**
 * Author: Anvar Olimov
 * User:user
 * Date:6/24/2024
 * Time:3:04 PM
 */


package dental.patientHistory.service;

import dental.client.service.ClientServiceImpl;
import dental.client.entity.Client;
import dental.epms.entity.Employees;
import dental.epms.service.EmployeeServiceImpl;
import dental.patientHistory.dto.PatientAddDto;
import dental.patientHistory.dto.PatientRequestDto;
import dental.patientHistory.dto.PatientResponseDto;
import dental.patientHistory.dto.PatientServiceAddDto;
import dental.patientHistory.entity.PatientHistoryEntity;
import dental.patientHistory.mapper.PatientHistoryMapper;
import dental.patientHistory.repository.PatientHistoryRepository;
import dental.serviceCategory.service.TServiceImpl;
import dental.teeth.service.TeethServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class PatientHistoryServiceImpl implements PatientHistoryService {


    private final PatientHistoryRepository repository;
    private final EmployeeServiceImpl employeeService;
    private final TeethServiceImpl teethService;
    private final ClientServiceImpl clientService;
    private final TServiceImpl tService;
    private final PatientHistoryMapper mapper;
    private final TeethServiceSImpl serviceS;

    @Override
    public List<PatientResponseDto> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public PatientResponseDto toDto(PatientHistoryEntity entity) {
        PatientResponseDto responseDto = mapper.toDto(entity);
        responseDto.setEmployeeResponseDto(employeeService.toDto(entity.getEmployees()));
        responseDto.setClientResponseDto(clientService.toDto(entity.getClient()));


        return responseDto;
    }

    @Override
    public List<PatientResponseDto> findByEmpId(Long id) {

        List<PatientHistoryEntity> patientHistoryEntities = repository.findByEmployeesId(id);
        return patientHistoryEntities.stream()
                .map(this::toDto)
                .toList();

    }

    @Override
    public PatientHistoryEntity getpPatientByid(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public void save(PatientHistoryEntity entity) {
        repository.save(entity);
    }

    @Override
    public PatientResponseDto create(@org.jetbrains.annotations.NotNull PatientAddDto dto) {

        Employees doctor = employeeService.getByID(dto.getEmployeeId());
        Client client = clientService.getClientByID(dto.getClientId());

        PatientHistoryEntity patientHistoryEntity = new PatientHistoryEntity();
        patientHistoryEntity.setCreatedAt(LocalDate.now());
        patientHistoryEntity.setEmployees(doctor);
        patientHistoryEntity.setPaid(0);
        patientHistoryEntity.setTotal(0);
        patientHistoryEntity.setIsPaid(false);
        patientHistoryEntity.setIsServiced(false);
        patientHistoryEntity.setClient(client);
        repository.save(patientHistoryEntity);

        return this.toDto(patientHistoryEntity);
    }

    @Override
    public String paid(Integer paid, Long id) {

        PatientHistoryEntity patientHistory = repository.findById(id).get();

        Integer pay = patientHistory.getPaid()  + paid;
        patientHistory.setPaid(pay);
        int debit = patientHistory.getTotal() - patientHistory.getPaid();
        if (debit<=0){
            patientHistory.setIsPaid(true);
        }
        return Integer.toString(debit);
    }

    @Override
    public void addService(PatientServiceAddDto dto) {
        serviceS.createTeethServiceForPatientHistory(dto);
    }


    @Override
    public PatientResponseDto update(@org.jetbrains.annotations.NotNull PatientRequestDto dto, Long id) {

        Employees doctor = employeeService.getByID(dto.getEmployeeId());
        Client client = clientService.getClientByID(dto.getClientId());
        PatientHistoryEntity patientHistoryEntity = new PatientHistoryEntity();
        patientHistoryEntity.setId(id);
        patientHistoryEntity.setEmployees(doctor);
        patientHistoryEntity.setIsServiced(false);
        patientHistoryEntity.setClient(client);

        repository.save(patientHistoryEntity);

        return this.toDto(patientHistoryEntity);

    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}

