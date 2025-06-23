package dental.service_category.service;

import dental.service_category.dto.ServiceRequestDto;
import dental.service_category.dto.ServiceRespondDto;
import dental.service_category.entity.ServiceEntity;

import java.util.List;

public interface TService {
    List<ServiceRespondDto> findAll();

    ServiceRespondDto getById(Long id);

    List<ServiceEntity> findAllService(List<Long> id);

    ServiceRespondDto create(ServiceRequestDto dto);

    ServiceRespondDto update(ServiceRequestDto dto, Long id);

    Integer countService();

    void delete(Long id);

    String activeDelete(Long id);

    ServiceRespondDto  toDto(ServiceEntity entity);

    ServiceEntity getEntityById(Long serviceId);
}

