package dental.epms.service;

import dental.epms.dto.*;
import dental.epms.entity.Employees;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface EmployeeService {


    List<EmployeeResponseDto> findAll();

    EmployeeResponseDto getEmpByID(Long empID);

    EmployeeRespondPassword getEmployeeRespondPassword(Long id);

    Employees getByID(Long id);

    EmployeeResponseDto toDto(Employees employees);

    Integer countEmployee();

    List<EmployeeResponseDto> getAllDoctors();

    String update(EmployeeUpdateRequest dto, Long id);



    void delete(Long id);

    UserDetailsService userDetailsService();

    String activeDelete(Long id);


}
