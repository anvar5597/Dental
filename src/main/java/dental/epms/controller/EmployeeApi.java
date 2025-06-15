package dental.epms.controller;

import dental.epms.dto.EmployeeRequestPassword;
import dental.epms.dto.EmployeeRespondPassword;
import dental.epms.dto.EmployeeResponseDto;
import dental.epms.dto.EmployeeUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RequestMapping("/employees")
public interface EmployeeApi {

    @GetMapping
    List<EmployeeResponseDto> getAll();


    @GetMapping("/{id}")
    EmployeeResponseDto findEmpByID(@PathVariable  Long id);
    @GetMapping("get-password/{id}")
    ResponseEntity<EmployeeRespondPassword> getEmployeePassword(@PathVariable  Long id);

    @GetMapping("/doctors")
    List<EmployeeResponseDto>  getAllDoctors();

    @GetMapping("/count-doctor")
    ResponseEntity<Integer> countEmployee();

    @PutMapping("/{id}")
    ResponseEntity<String> update(@RequestBody EmployeeUpdateRequest entity , @PathVariable Long id);


    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id );

    @DeleteMapping("/passive-delete/{id}")
    ResponseEntity<String> activeDelete(@PathVariable Long id);


}
