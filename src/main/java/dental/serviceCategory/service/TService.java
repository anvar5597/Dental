package dental.serviceCategory.service;

import dental.serviceCategory.dto.ServiceRequestDto;
import dental.serviceCategory.dto.ServiceRespondDto;

import java.util.List;

public interface TService {
    List<ServiceRespondDto> findAll();

    ServiceRespondDto getById(Long id);

    ServiceRespondDto create(ServiceRequestDto dto);

    ServiceRespondDto update(ServiceRequestDto dto, Long id);

    void delete(Long id);
}
