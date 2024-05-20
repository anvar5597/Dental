package dental.epms.controller;

import dental.epms.dto.EmployeeRequestDto;
import dental.epms.repository.EmployeeRepository;
import dental.epms.service.EmployeeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import dental.epms.entity.EmployeeEntity;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmployeeController implements EmployeeApi {

    private final EmployeeServiceImpl employeeService;
    private final EmployeeRepository employeeRepository;


    @Override
    public List<EmployeeEntity> getAll() {
//        List<?> employeeEntities = employeeService.findAll();
//        return (List<EmployeeEntity>) employeeEntities;
    return employeeRepository.findAll();

    }

//    @Override
//    public ResponseEntity<EmployeeEntity> create(EmployeeRequestDto entity) {
//        EmployeeEntity employeeEntity = employeeService.create(entity);
//        return ResponseEntity.ok(employeeEntity);
//    }

    @Override
    public void create(EmployeeRequestDto entity) {
        employeeService.create(entity);
    }

    @Override
    public void update(EmployeeRequestDto entity, Long id ) {
        employeeService.update(entity , id);
    }


    @Override
    public void delete(@PathVariable Long id) {

        employeeService.delete(id);

    }
}
