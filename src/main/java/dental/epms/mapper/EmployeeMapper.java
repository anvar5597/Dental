package dental.epms.mapper;

import dental.epms.dto.EmployeeRequestDto;
import dental.epms.entity.EmployeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface EmployeeMapper {

    EmployeeEntity toEntity(EmployeeRequestDto dto);

    EmployeeRequestDto toDto(EmployeeEntity entity);

    EmployeeEntity update(@MappingTarget EmployeeEntity entity, EmployeeRequestDto dto);


}
