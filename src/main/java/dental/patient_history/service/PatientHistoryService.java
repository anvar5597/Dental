package dental.patient_history.service;

import dental.client.entity.Client;
import dental.epms.dto.EmployeeShortInfoDto;
import dental.patient_history.dto.*;
import dental.patient_history.entity.PatientHistoryEntity;
import dental.utils.DefaultResponseDto;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface PatientHistoryService {

    List<PatientResponseDto> findAll();

    public List<PatientShortInfoDto> findAllShort();

    List<PatientResponseDto> findByEmpId(Long id);

    PatientHistoryEntity getPatientById(Long id);

    PatientResponseDto findById(Long id);

    void save(PatientHistoryEntity entity);

    Integer countClientNotServiced();

    List<MonthlyCountDTO> getMonthlyAppointmentCount();

    public List<MonthlyIncomeExpenseDTO> getMonthlyIncomeAndExpensePerEmployee();

    Integer countClientServiced();
    EmployeeShortInfoDto findDoctorId(String token);
    String create(@org.jetbrains.annotations.NotNull PatientAddDto dto);

    String serviced(Long id);

    DefaultResponseDto addService(PatientServiceAddDto dto);

    public PatientShortInfoDto toShortDto(@NotNull PatientHistoryEntity entity);
    String update(PatientRequestDto dto, Long id);

    Boolean hasClient(Client client);
    String delete(Long id);

    void deleteWithEmployee(Long id);

    void deleteWithClient(Long id);
}
