package dental.epms.controller;

import dental.epms.dto.EmployeeRequestDto;
import dental.epms.dto.EmployeeResponseDto;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RequestMapping("/employees")
public interface EmployeeApi {

    @GetMapping
    List<EmployeeResponseDto> getAll();


    @GetMapping("/{id}")
    EmployeeResponseDto findEmpByID(@PathVariable  Long id);

    @GetMapping("/doctors")
  List<EmployeeResponseDto>  getAllDoctors();

    @PutMapping("/{id}")
    void update(@RequestBody EmployeeRequestDto entity , @PathVariable Long id);

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id );


}
