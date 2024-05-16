package dental.epms.controller;

import dental.epms.entity.EmployeeEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.List;

@RequestMapping("/employees")
public interface EmployeeApi {

    @GetMapping
    List<?> getAll();

    @PostMapping
    void create(@RequestBody EmployeeEntity entity);
}
