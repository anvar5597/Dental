package dental.epms;

import dental.epms.dto.EmployeeDto;
import dental.epms.entity.EmployeeEntity;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface EmployeeMapper {

    EmployeeEntity toEntity(EmployeeDto dto);

    EmployeeDto toDto(EmployeeEntity entity);
}
