package dental.service_category.service;

import dental.exception.ResourceNotFoundException;
import dental.service_category.dto.ServiceRequestDto;
import dental.service_category.dto.ServiceRespondDto;
import dental.service_category.entity.ServiceEntity;
import dental.service_category.mapper.ServiceMapper;
import dental.service_category.repository.ServiceRepository;
import dental.teeth.service.TeethService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
                .filter(ServiceEntity::getActive)
                .map(mapper::toServiceRespondDto)
                .toList();
    }

    @Override
    public ServiceRespondDto getById(Long id) {
        return repository.findById(id)
                .filter(ServiceEntity::getActive)
                .map(mapper::toServiceRespondDto)
                .get();
    }

    @Override
    public List<ServiceEntity> findAllService(List<Long> ids) {
        return ids.stream()
                .map(repository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(ServiceEntity::getActive)
                .toList();
    }

    @Override
    public ServiceRespondDto create(ServiceRequestDto dto) {
        return Optional.ofNullable(dto)
                .map(mapper::toEntity)
                .map(entity -> {
                    entity.setCreatedAt(LocalDateTime.now());
                    return repository.save(entity);
                })
                .map(mapper::toServiceRespondDto)
                .orElseThrow(() -> new IllegalArgumentException("DTO must not be null"));
    }

    @Override
    public ServiceRespondDto update(ServiceRequestDto dto, Long id) {
       Optional<ServiceEntity> optionalServiceEntity = repository.findById(id);
       if (optionalServiceEntity.isEmpty()){
           throw new ResourceNotFoundException("Bunday id raqamli service yo`q");
       }
       else{
           ServiceEntity serviceEntity = optionalServiceEntity.get();
            serviceEntity.setActive(false);
            repository.save(serviceEntity);
       }
        return Optional.ofNullable(dto)
                .map(mapper::toEntity)
                .map(entity -> {
                    entity.setCreatedAt(LocalDateTime.now());
                    return repository.save(entity);
                })
                .map(mapper::toServiceRespondDto)
                .orElseThrow(() -> new IllegalArgumentException("DTO must not be null"));

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
    public String activeDelete(Long id) {
        Optional<ServiceEntity> optionalServiceEntity = repository.findById(id);
        if (optionalServiceEntity.isEmpty()){
            throw new ResourceNotFoundException("Bunday id raqamli service yo`q");
        }
        ServiceEntity serviceEntity = optionalServiceEntity.get();
        serviceEntity.setActive(false);
        repository.save(serviceEntity);
        return "O`chirildi";

    }

    @Override
    public ServiceRespondDto toDto(ServiceEntity entity) {
        return mapper.toServiceRespondDto(entity);
    }

    @Override
    public ServiceEntity getEntityById(Long serviceId) {
        Optional<ServiceEntity> entity = repository.findById(serviceId);
        if (entity.isEmpty()){
            throw new ResourceNotFoundException("Bunday id raqamli service yo");
        }
        return entity.get();
    }



}
