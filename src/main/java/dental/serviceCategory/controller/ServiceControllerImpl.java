package dental.serviceCategory.controller;

import dental.serviceCategory.dto.ServiceRequestDto;
import dental.serviceCategory.dto.ServiceRespondDto;
import dental.serviceCategory.service.TService;
import lombok.RequiredArgsConstructor;
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
    public ServiceRespondDto create(ServiceRequestDto dto) {
        return teethService.create(dto);
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
