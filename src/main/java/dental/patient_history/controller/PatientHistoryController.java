package dental.patient_history.controller;


import dental.epms.dto.EmployeeShortInfoDto;
import dental.patient_history.dto.*;
import dental.utils.DefaultResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/patient")
public interface PatientHistoryController {

    @GetMapping("find-all")
    ResponseEntity<List<PatientResponseDto>> findAll();

    @GetMapping("find-all-after")
    ResponseEntity<List<PatientResponseDto>> findAllAfter();


    @GetMapping("find-all-short")
    ResponseEntity<List<PatientShortInfoDto>> findAllShort();

    @GetMapping("doctor/{id}")
    List<PatientResponseDto> findByEmpId(@PathVariable Long id);

    @GetMapping("doctor-not-serviced/{id}")
    ResponseEntity<List<PatientResponseDto>> findByEmpIdIsServiced(@PathVariable Long id);

    @GetMapping("/find-client/{id}")
    ResponseEntity<List<PatientResponseDto>> findByClientId(@PathVariable Long id);

    @GetMapping("/find-debt")
    ResponseEntity<List<PatientResponseDto>> findDebtPatient();
    @GetMapping("/{id}")
    ResponseEntity<PatientResponseDto> findById(@PathVariable Long id);

    @PostMapping("/create")
    ResponseEntity<String> create(@RequestBody PatientAddDto dto);

    @GetMapping("/clients-not-serviced")
    Integer countClientNotServiced();
    @GetMapping("/clients-serviced")
    Integer countClientServiced();

    @GetMapping("/clients")
    Integer countClient();

    @GetMapping("/monthly-appointments")
    List<MonthlyCountDTO> getMonthlyAppointmentCount();

    @GetMapping("/monthly-total-income-expense")
    public List<MonthlyIncomeExpenseDTO> getMonthlyTotalIncomeAndExpense();

    @GetMapping("/monthly-income-expense")
    List<MonthlyIncomeExpenseDTO> getMonthlyIncomeAndExpensePerEmployee() ;

    @GetMapping("/find-by-token")
    ResponseEntity<EmployeeShortInfoDto> findDoctorByToken(String token);


    @PutMapping("/add-service")
    DefaultResponseDto addService(@RequestBody PatientServiceAddDto dto);

    @PutMapping("/serviced/{id}")
    ResponseEntity<String> serviced(@PathVariable Long id);


    @PutMapping("update/{id}")
    ResponseEntity<String> update(@RequestBody PatientRequestDto dto, @PathVariable Long id);

    @DeleteMapping("/{id}")
    ResponseEntity<String> delete(@PathVariable Long id);

    @DeleteMapping("/passive-delete/{id}")
    ResponseEntity<String> activeDelete(@PathVariable Long id);
}
