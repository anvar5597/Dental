package dental.serviceCategory.controller;


import dental.serviceCategory.dto.ServiceRequestDto;
import dental.serviceCategory.dto.ServiceRespondDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/service")
public interface ServiceController {

    @GetMapping
    List<ServiceRespondDto> findAll();

    @GetMapping("/{id}")
    ServiceRespondDto getById(@PathVariable Long id);


    @PostMapping
    ServiceRespondDto create(@RequestBody ServiceRequestDto dto);


    @PutMapping("/{id}")
    ServiceRespondDto update(@RequestBody ServiceRequestDto dto, @PathVariable Long id);

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id);

}
