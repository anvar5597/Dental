package dental.epms.service;

import dental.epms.dto.EmployeeRequestDto;
import dental.epms.dto.EmployeeResponseDto;
import dental.epms.entity.Employees;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface EmployeeService {

    List<EmployeeResponseDto> findAll();

    EmployeeResponseDto getEmpByID(Long empID);

    Employees getByID(Long id);

    EmployeeResponseDto toDto(Employees employees);


    List<EmployeeResponseDto> getAllDoctors();

    Employees update(EmployeeRequestDto dto, Long id);

    void delete(Long id);

    UserDetailsService userDetailsService();


}
