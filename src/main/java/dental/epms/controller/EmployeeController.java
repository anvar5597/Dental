package dental.epms.controller;

import dental.epms.dto.EmployeeRequestDto;
import dental.epms.dto.EmployeeResponseDto;
import dental.epms.service.EmployeeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmployeeController implements EmployeeApi {

    private final EmployeeServiceImpl employeeService;

    @Override
    public List<EmployeeResponseDto> getAll() {
        return employeeService.findAll();
    }

    @Override
    public List<EmployeeResponseDto> getAllDoctors() {
        return employeeService.getAllDoctors();
    }

    @Override
    public EmployeeResponseDto findEmpByID(Long id) {
        return employeeService.getEmpByID(id);
    }

    @Override
    public void update(EmployeeRequestDto entity, Long id) {
        employeeService.update(entity, id);
    }

    @Override
    public void delete(@PathVariable Long id) {
        employeeService.delete(id);
    }
}
