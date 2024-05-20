package dental.epms.service;

import dental.epms.dto.EmployeeRequestDto;
import dental.epms.entity.EmployeeEntity;
import dental.epms.mapper.EmployeeMapper;
import dental.epms.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    private final EmployeeMapper mapper;

    @Override
    public List<?> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public EmployeeEntity create(EmployeeRequestDto dto) {

        Optional.ofNullable(dto)
                .map(mapper::toEntity)
                .map(repository::save);
        return mapper.toEntity(dto);
    }

    @Override
    public EmployeeEntity update(EmployeeRequestDto dto, Long id) {

//        Optional.ofNullable(dto)
//                .map(mapper::toEntity)
//                .map(repository::save);

//        EmployeeEntity employee = repository.findByFirstName();
        return Optional.ofNullable(id)
                .flatMap(repository::findById)
                .map(entity -> mapper.update(entity, dto))
                .map(repository::save)
                .orElseThrow();

//        return mapper.toEntity(dto);
    }

    @Override
    public void delete(Long id) {
        EmployeeEntity employee = repository.getOne(id);
        repository.delete(employee);
    }
}
