package dental.patient_history.service;

import dental.patient_history.dto.*;
import dental.patient_history.entity.PatientHistoryEntity;
import dental.payment.dto.PaymentResponseDto;
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

    Long findDoctorId(String token);
    String create(@org.jetbrains.annotations.NotNull PatientAddDto dto);

    String serviced(Long id);

    DefaultResponseDto addService(PatientServiceAddDto dto);

    public PatientShortInfoDto toShortDto(@NotNull PatientHistoryEntity entity);
    String update(PatientRequestDto dto, Long id);

    String delete(Long id);
}
