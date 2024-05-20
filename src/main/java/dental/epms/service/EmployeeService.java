package dental.epms.service;

import dental.epms.dto.EmployeeRequestDto;
import dental.epms.entity.EmployeeEntity;

import java.util.List;

public interface EmployeeService {

   List<?> findAll();

   EmployeeEntity create(EmployeeRequestDto dto);

    EmployeeEntity update(EmployeeRequestDto dto , Long id);

   void delete(Long id);

}
