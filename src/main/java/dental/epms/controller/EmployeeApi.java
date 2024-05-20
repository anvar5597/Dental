package dental.epms.controller;

import dental.epms.dto.EmployeeRequestDto;
import dental.epms.entity.EmployeeEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RequestMapping("/employees")
public interface EmployeeApi {

    @GetMapping
    List<?> getAll();

    @PostMapping
    void  create(@RequestBody EmployeeRequestDto entity);
//    public ResponseEntity<EmployeeEntity>  create(@RequestBody EmployeeRequestDto entity);
    @PutMapping("/{id}")
    void update(@RequestBody EmployeeRequestDto entity , @PathVariable Long id);

@DeleteMapping("/{id}")
    void delete(@PathVariable Long id );

}
