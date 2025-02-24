package dental.service_category.controller;


import dental.service_category.dto.ServiceRequestDto;
import dental.service_category.dto.ServiceRespondDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/service")
public interface ServiceController {

    @GetMapping
    List<ServiceRespondDto> findAll();

    @GetMapping("/{id}")
    ServiceRespondDto getById(@PathVariable Long id);


    @PostMapping
    String create(@RequestBody ServiceRequestDto dto);


    @PutMapping("/{id}")
    ServiceRespondDto update(@RequestBody ServiceRequestDto dto, @PathVariable Long id);

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id);

}
