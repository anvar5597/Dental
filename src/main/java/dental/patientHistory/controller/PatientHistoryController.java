package dental.patientHistory.controller;


import dental.patientHistory.dto.PatientAddDto;
import dental.patientHistory.dto.PatientRequestDto;
import dental.patientHistory.dto.PatientResponseDto;
import dental.patientHistory.dto.PatientServiceAddDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/patient")
public interface PatientHistoryController {

    @GetMapping("find-all")
    List<PatientResponseDto> findAll();

    @GetMapping("/{id}")
    List<PatientResponseDto> findByEmpId(@PathVariable Long id);

    @PostMapping("/create")
    PatientResponseDto create(@RequestBody PatientAddDto dto);


    @PutMapping("/add-service")
    ResponseEntity<String> addService(@RequestBody PatientServiceAddDto dto);


    @PutMapping("update/{id}")
    PatientResponseDto update(@RequestBody PatientRequestDto dto, @PathVariable Long id);

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id);
}
