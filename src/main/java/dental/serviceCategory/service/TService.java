package dental.serviceCategory.service;

import dental.patientHistory.dto.TeethServiceDto;
import dental.patientHistory.entity.PatientHistoryEntity;
import dental.serviceCategory.dto.ServiceRequestDto;
import dental.serviceCategory.dto.ServiceRespondDto;
import dental.serviceCategory.entity.ServiceEntity;

import java.util.List;

public interface TService {
    List<ServiceRespondDto> findAll();

    ServiceRespondDto getById(Long id);

    List<ServiceEntity> findAllService(List<Long> id);

    ServiceRespondDto create(ServiceRequestDto dto);

    ServiceRespondDto update(ServiceRequestDto dto, Long id);

    void delete(Long id);

    ServiceRespondDto  toDto(ServiceEntity entity);

    ServiceEntity getEntityById(Long serviceId);
}

