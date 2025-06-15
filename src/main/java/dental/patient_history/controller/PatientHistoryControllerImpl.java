/**
 * Author: Anvar Olimov
 * User:user
 * Date:6/24/2024
 * Time:5:33 PM
 */


package dental.patient_history.controller;

import dental.epms.dto.EmployeeShortInfoDto;
import dental.patient_history.dto.*;
import dental.patient_history.service.PatientHistoryServiceImpl;
import dental.utils.DefaultResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PatientHistoryControllerImpl implements PatientHistoryController {

    private final PatientHistoryServiceImpl patientHistoryService;

    @Override
    public ResponseEntity<List<PatientResponseDto>> findAll() {
        return ResponseEntity.ok(patientHistoryService.findAll());
    }

    @Override
    public ResponseEntity<List<PatientResponseDto>> findAllAfter() {
        return ResponseEntity.ok(patientHistoryService.findAllAfter());
    }

    @Override
    public ResponseEntity<List<PatientShortInfoDto>> findAllShort() {
        return ResponseEntity.ok(patientHistoryService.findAllShort());
    }

    public List<MonthlyIncomeExpenseDTO> getMonthlyTotalIncomeAndExpense() {
        return patientHistoryService.getMonthlyTotalIncomeAndExpense();
    }

    @Override
    public List<PatientResponseDto> findByEmpId(Long id) {
        return patientHistoryService.findByEmpId(id);
    }

    @Override
    public ResponseEntity<List<PatientResponseDto>> findByEmpIdIsServiced(Long id) {
        return ResponseEntity.ok(patientHistoryService.findByEmpIdIsServiced(id)) ;
    }

    @Override
    public ResponseEntity<List<PatientResponseDto>> findByEmpIdIsNotServiced(Long id) {

        return ResponseEntity.ok(patientHistoryService.findByEmpIdIsNotServiced(id)) ;
    }

    @Override
    public ResponseEntity<List<PatientResponseDto>> findByClientId(Long id) {
        return ResponseEntity.ok(patientHistoryService.findByClientId(id));
    }

    @Override
    public ResponseEntity<List<PatientResponseDto>> findDebtPatient() {
        return ResponseEntity.ok(patientHistoryService.findDebitPatient());
    }

    @Override
    public ResponseEntity<PatientResponseDto> findById(Long id) {
        return ResponseEntity.ok(patientHistoryService.findById(id));
    }

    @Override
    public ResponseEntity<String> create(PatientAddDto dto) {
        return ResponseEntity.ok(patientHistoryService.create(dto));
    }

    @Override
    public Integer countClientNotServiced() {
        return patientHistoryService.countClientNotServiced();
    }

    @Override
    public Integer countClientServiced() {
        return patientHistoryService.countClientServiced();
    }

    @Override
    public Integer countClient() {
        return (patientHistoryService.countClientServiced() + patientHistoryService.countClientNotServiced());
    }

    @Override
    public List<MonthlyCountDTO> getMonthlyAppointmentCount() {
        return patientHistoryService.getMonthlyAppointmentCount();
    }

    @Override
    public List<MonthlyIncomeExpenseDTO> getMonthlyIncomeAndExpensePerEmployee() {
        return patientHistoryService.getMonthlyIncomeAndExpensePerEmployee();
    }

    @Override
    public ResponseEntity<EmployeeShortInfoDto> findDoctorByToken(String token) {
        return ResponseEntity.ok(patientHistoryService.findDoctorId(token));
    }

    @Override
    public DefaultResponseDto addService(PatientServiceAddDto dto) {
        return patientHistoryService.addService(dto);
    }

    @Override
    public ResponseEntity<String> serviced(Long id) {
        return ResponseEntity.ok(patientHistoryService.serviced(id));
    }

    @Override
    public ResponseEntity<String> update(PatientRequestDto dto, Long id) {
        return ResponseEntity.ok(patientHistoryService.update(dto, id));
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        return ResponseEntity.ok(patientHistoryService.delete(id));
    }

    @Override
    public ResponseEntity<String> activeDelete(Long id) {
        return ResponseEntity.ok(patientHistoryService.activeDelete(id));
    }
}
