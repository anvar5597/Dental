package dental.epms.controller;

import dental.epms.dto.EmployeeRequestPassword;
import dental.epms.dto.EmployeeRespondPassword;
import dental.epms.dto.EmployeeResponseDto;
import dental.epms.dto.EmployeeUpdateRequest;
import dental.epms.service.EmployeeServiceImpl;
import dental.patient_history.service.PatientHistoryService;
import dental.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<EmployeeRespondPassword> getEmployeePassword(Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeRespondPassword(id));
    }

    @Override
    public ResponseEntity<String> update(EmployeeUpdateRequest entity, Long id) {
       return ResponseEntity.ok(employeeService.update(entity, id));
    }



    @Override
    public void delete(Long id) {
        historyService.deleteWithEmployee(id);
         paymentService.deleteWithEmployee(id);
        employeeService.delete(id);
    }

    @Override
    public ResponseEntity<String> activeDelete(Long id) {
        return ResponseEntity.ok(employeeService.activeDelete(id));
    }
}
