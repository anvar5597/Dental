package dental.epms.service;

import dental.epms.dto.EmployeeRequestDto;
import dental.epms.dto.EmployeeResponseDto;
import dental.epms.entity.ERole;
import dental.epms.entity.Employees;
import dental.epms.mapper.EmployeeMapper;
import dental.epms.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    private final EmployeeMapper mapper;


    @Override
    public List<EmployeeResponseDto> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public EmployeeResponseDto getEmpByID(Long empID) {
        return repository.findById(empID)
                .map(mapper::toDto)
                .get();
    }

    @Override
    public List<EmployeeResponseDto> getAllDoctors() {
        return repository.findAllByRole(ERole.ROLE_USER)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public Employees update(EmployeeRequestDto dto, Long id) {

        return Optional.ofNullable(id)
                .flatMap(repository::findById)
                .map(entity -> mapper.update(entity, dto))
                .map(repository::save)
                .orElseThrow();

    }

    @Override
    public void delete(Long id) {
        Employees employee = repository.getReferenceById(id);
        repository.delete(employee);
    }

    @Override
    public UserDetailsService userDetailsService() {
        return username -> (UserDetails) repository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


}
