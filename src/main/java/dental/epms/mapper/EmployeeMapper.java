package dental.epms.mapper;

import dental.epms.dto.EmployeeRequestDto;
import dental.epms.dto.EmployeeRequestPassword;
import dental.epms.dto.EmployeeResponseDto;
import dental.epms.dto.LoginDto;
import dental.epms.entity.Employees;

import org.mapstruct.Mapper;

import org.mapstruct.MappingTarget;


import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface EmployeeMapper {

    Employees toEntity(EmployeeRequestDto dto);

    EmployeeResponseDto toDto(Employees entity);

    LoginDto toLoginDto(Employees employees);

    Employees update(@MappingTarget Employees entity, EmployeeRequestDto dto);


    Employees update(@MappingTarget Employees entity, EmployeeRequestPassword dto);



}
