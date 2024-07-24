package dental.teeth.service;

import dental.patientHistory.dto.TeethServiceDto;
import dental.patientHistory.entity.PatientHistoryEntity;
import dental.serviceCategory.entity.ServiceEntity;
import dental.teeth.dto.TeethRequestDto;
import dental.teeth.dto.TeethResponseDto;
import dental.teeth.entity.TeethEntity;

import java.util.List;

public interface TeethService {

    List<TeethResponseDto> findAll();

    TeethResponseDto getById(Long id);

    TeethEntity getTeethById(Long id);

    Long create(TeethRequestDto dto);

    TeethEntity update(TeethRequestDto dto, Long id);

    void delete(Long id);

    TeethResponseDto toDto(TeethEntity entity);




}
