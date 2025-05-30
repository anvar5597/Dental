package dental.service_category.controller;

import dental.service_category.dto.ServiceRequestDto;
import dental.service_category.dto.ServiceRespondDto;
import dental.service_category.service.TService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ServiceControllerImpl implements ServiceController{

    private final TService teethService;
    @Override
    public List<ServiceRespondDto> findAll() {
        return teethService.findAll();
    }

    @Override
    public ServiceRespondDto getById(Long id) {
        return teethService.getById(id);
    }

    @Override
    public ResponseEntity<Integer> countServices() {
        return ResponseEntity.ok(teethService.countService());
    }

    @Override
    public String create(ServiceRequestDto dto) {
        teethService.create(dto);
        return "Servis yaratildi";
    }

    @Override
    public ServiceRespondDto update(ServiceRequestDto dto, Long id) {
        return teethService.update(dto,id);
    }

    @Override
    public void delete(Long id) {
            teethService.delete(id);
    }
}
