package dental.teeth.service;

import dental.exception.ResourceNotFoundException;
import dental.teeth.dto.TeethRequestDto;
import dental.teeth.dto.TeethResponseDto;
import dental.teeth.entity.TeethEntity;
import dental.teeth.mapper.TeethMapper;
import dental.teeth.teethRepository.TeethRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class    TeethServiceImpl implements TeethService {

    private final TeethRepository repository;
    private final TeethMapper mapper;

    @Override
    public List<TeethResponseDto> findAll() {
        return repository.findAll()
                .stream()
                .filter(TeethEntity::getActive)
                .map(mapper::toDto).
                toList();
    }

    @Override
    public TeethResponseDto getById(Long id) {
            return Optional.ofNullable(id)
                    .flatMap(repository::findById)
                    .map(mapper::toDto)
                    .get();

    }

    @Override
    public TeethEntity getTeethById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public Long create(TeethRequestDto dto) {

        return Optional.ofNullable(dto)
                .map(mapper::toEntity)
                .map(repository::save)
                .map(TeethEntity::getId)
                .orElseThrow();
    }

    @Override
    public TeethEntity update(TeethRequestDto dto, Long id) {

        return Optional.ofNullable(id)
                .flatMap(repository::findById)
                .map(entity -> mapper.update(entity, dto))
                .map(repository::save)
                .orElseThrow();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public TeethResponseDto toDto(TeethEntity entity) {
        return mapper.toDto(entity);
    }

    @Override
    public String activeDelete(Long id) {
        Optional<TeethEntity> optionalTeethEntity = repository.findById(id);
        if (optionalTeethEntity.isEmpty()){
            throw new ResourceNotFoundException("Bunday id raqamli servis yo`q");
        }
        TeethEntity teethEntity = optionalTeethEntity.get();
        teethEntity.setActive(false);
        repository.save(teethEntity);
        return "O`chirildi";
    }


}
