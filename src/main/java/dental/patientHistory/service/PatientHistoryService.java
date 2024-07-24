package dental.patientHistory.service;

import dental.patientHistory.dto.*;
import dental.patientHistory.entity.PatientHistoryEntity;

import java.util.List;

public interface PatientHistoryService {

    List<PatientResponseDto> findAll();


    List<PatientResponseDto> findByEmpId(Long id);

    PatientHistoryEntity getpPatientByid(Long id);

    void save(PatientHistoryEntity entity);


    PatientResponseDto create(@org.jetbrains.annotations.NotNull PatientAddDto dto);


    String paid(Integer paid , Long id);

    void addService(PatientServiceAddDto dto);


    PatientResponseDto update(PatientRequestDto dto, Long id);

    void delete(Long id);
}
