package dental.serviceCategory.service;

import dental.serviceCategory.dto.ServiceRequestDto;
import dental.serviceCategory.dto.ServiceRespondDto;
import dental.serviceCategory.entity.ServiceEntity;
import dental.serviceCategory.mapper.ServiceMapper;
import dental.serviceCategory.repository.ServiceRepository;
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
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
