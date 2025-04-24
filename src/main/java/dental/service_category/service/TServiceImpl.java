package dental.service_category.service;

import dental.service_category.dto.ServiceRequestDto;
import dental.service_category.dto.ServiceRespondDto;
import dental.service_category.entity.ServiceEntity;
import dental.service_category.mapper.ServiceMapper;
import dental.service_category.repository.ServiceRepository;
import dental.teeth.service.TeethService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional

@RequiredArgsConstructor
public class TServiceImpl implements TService {

    private final ServiceRepository repository;

    private final ServiceMapper mapper;

    private final TeethService teethService;

    @Override
    public List<ServiceRespondDto> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toServiceRespondDto)
                .toList();
    }

    @Override
    public ServiceRespondDto getById(Long id) {
        return repository.findById(id)
                .map(mapper::toServiceRespondDto)
                .get();
    }

    @Override
    public List<ServiceEntity> findAllService(List<Long> ids) {
        return ids.stream()
                .map(repository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    @Override
    public ServiceRespondDto create(ServiceRequestDto dto) {
        Optional.ofNullable(dto)
                .map(mapper::toEntity)
                .map(repository::save);
        ServiceEntity service = mapper.toEntity(dto);
        return mapper.toServiceRespondDto(service);
    }

    @Override
    public ServiceRespondDto update(ServiceRequestDto dto, Long id) {
        Optional.ofNullable(id)
                .flatMap(repository::findById)
                .map(entity -> mapper.update(entity, dto))
                .map(repository::save);

        ServiceEntity service = mapper.toEntity(dto);
        return mapper.toServiceRespondDto(service);
    }

    @Override
    public Integer countService() {

        List<ServiceEntity> entities = repository.findAll();
        return entities.size();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public ServiceRespondDto toDto(ServiceEntity entity) {
        return mapper.toServiceRespondDto(entity);
    }

    @Override
    public ServiceEntity getEntityById(Long serviceId) {
        return repository.findById(serviceId).get();
    }



}
