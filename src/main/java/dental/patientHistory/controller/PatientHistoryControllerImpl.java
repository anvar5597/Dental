/**
 * Author: Anvar Olimov
 * User:user
 * Date:6/24/2024
 * Time:5:33 PM
 */


package dental.patientHistory.controller;

import dental.patientHistory.dto.PatientAddDto;
import dental.patientHistory.dto.PatientRequestDto;
import dental.patientHistory.dto.PatientResponseDto;
import dental.patientHistory.dto.PatientServiceAddDto;
import dental.patientHistory.service.PatientHistoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PatientHistoryControllerImpl implements  PatientHistoryController{

    private final PatientHistoryServiceImpl patientHistoryService;
    @Override
    public List<PatientResponseDto> findAll() {
        return  patientHistoryService.findAll();
    }

    @Override
    public List<PatientResponseDto> findByEmpId(Long id) {
        return patientHistoryService.findByEmpId(id);
    }

    @Override
    public PatientResponseDto create(PatientAddDto dto) {
        return patientHistoryService.create(dto);
    }

    @Override
    public ResponseEntity<String> addService(PatientServiceAddDto dto) {
        patientHistoryService.addService(dto);
        return ResponseEntity.ok("service qo`shildi");
    }


    @Override
    public PatientResponseDto update(PatientRequestDto dto, Long id) {
        return patientHistoryService.update(dto, id);
    }

    @Override
    public void delete(Long id) {
        patientHistoryService.delete(id);
    }
}
