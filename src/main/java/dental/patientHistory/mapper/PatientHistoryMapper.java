package dental.patientHistory.mapper;


import dental.patientHistory.dto.PatientAddDto;
import dental.patientHistory.dto.PatientRequestDto;
import dental.patientHistory.dto.PatientResponseDto;
import dental.patientHistory.entity.PatientHistoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface PatientHistoryMapper {

    PatientHistoryEntity toEntity(PatientRequestDto dto);

    PatientResponseDto toDto(PatientHistoryEntity entity);

    PatientHistoryEntity toEntityForManager(PatientAddDto dto);

    PatientHistoryEntity toEntityAddService(PatientAddDto dto);


    PatientHistoryEntity update(@MappingTarget PatientHistoryEntity entity, PatientRequestDto dto);
}
