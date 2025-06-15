package dental.epms.service;

import dental.epms.dto.EmployeeRespondPassword;
import dental.epms.dto.EmployeeResponseDto;
import dental.epms.dto.EmployeeUpdateRequest;
import dental.epms.entity.ERole;
import dental.epms.entity.Employees;
import dental.epms.mapper.EmployeeMapper;
import dental.epms.repository.EmployeeRepository;
import dental.exception.ResourceNotFoundException;
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
                .filter(Employees::getActive)
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public EmployeeResponseDto getEmpByID(Long empID) {

        return repository.findById(empID)
                .map(mapper::toDto)
                .orElse(null);
    }
    @Override
    public EmployeeRespondPassword getEmployeeRespondPassword(Long id) {
        Employees employees = repository.getReferenceById(id);

        EmployeeRespondPassword employeeRespondPassword = new EmployeeRespondPassword();
        employeeRespondPassword.setId(id);
        employeeRespondPassword.setLogin(employees.getLogin());
        employeeRespondPassword.setPassword(employees.getOpenPassword());
        return employeeRespondPassword;
    }

    @Override
    public Employees getByID(Long id) {
        Optional<Employees> employees = repository.findById(id);
        if (employees.isEmpty()){
            throw new ResourceNotFoundException("Bunday doctor yo`q");
        }
        return employees.get();
    }

    @Override
    public List<EmployeeResponseDto> getAllDoctors() {
        return repository.findAllByRole(ERole.ROLE_USER)
                .stream()
                .filter(Employees::getActive)
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public String update(EmployeeUpdateRequest dto, Long id) {
        Employees employees = repository.getReferenceById(id);
        employees.setFirstName(dto.getFirstName());
        employees.setLastName(dto.getLastName());
        employees.setPatronymic(dto.getPatronymic());
        employees.setEmail(dto.getEmail());
        employees.setRole(dto.getRole());
        employees.setBirthDay(dto.getBirthDay());
        employees.setPhoneNumber(dto.getPhoneNumber());
        employees.setAddress(dto.getAddress());
        repository.save(employees);

        return "Update";

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

    @Override
    public String activeDelete(Long id) {
        Optional<Employees> optionalEmployees = repository.findById(id);
        if (optionalEmployees.isEmpty()){
            throw new ResourceNotFoundException("Bunday doctor yo`q");
        }
        Employees employees = optionalEmployees.get();
        employees.setActive(false);
        repository.save(employees);
        return "O`chirildi";
    }

    @Override
    public EmployeeResponseDto toDto(Employees employees) {

        return mapper.toDto(employees);
    }

    @Override
    public Integer countEmployee() {

        List<Employees> employees = repository.findAllByRole(ERole.ROLE_USER);
        return employees.size();
    }
}
