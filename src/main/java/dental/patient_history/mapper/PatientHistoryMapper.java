package dental.patient_history.mapper;


import dental.patient_history.dto.PatientAddDto;
import dental.patient_history.dto.PatientRequestDto;
import dental.patient_history.dto.PatientResponseDto;
import dental.patient_history.entity.PatientHistoryEntity;
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
