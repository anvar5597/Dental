package dental.epms.controller;

import dental.epms.dto.EmployeeRequestDto;
import dental.epms.dto.EmployeeResponseDto;
import dental.epms.service.EmployeeServiceImpl;
import dental.patient_history.service.PatientHistoryService;
import dental.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmployeeController implements EmployeeApi {

    private final EmployeeServiceImpl employeeService;
    private final PatientHistoryService historyService;
    private final PaymentService paymentService;

    @Override
    public List<EmployeeResponseDto> getAll() {
        return employeeService.findAll();
    }

    @Override
    public List<EmployeeResponseDto> getAllDoctors() {
        return employeeService.getAllDoctors();
    }

    @Override
    public ResponseEntity<Integer> countEmployee() {

        return ResponseEntity.ok(employeeService.countEmployee());
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
        historyService.deleteWithEmployee(id);
         paymentService.deleteWithEmployee(id);
        employeeService.delete(id);
    }
}
