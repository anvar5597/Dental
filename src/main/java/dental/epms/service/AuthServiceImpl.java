package dental.epms.service;


import dental.epms.dto.EmployeeRequestDto;
import dental.epms.dto.EmployeeResponseDto;
import dental.epms.dto.LoginDto;
import dental.epms.entity.Employees;
import dental.epms.mapper.EmployeeMapper;
import dental.epms.repository.EmployeeRepository;
import dental.epms.repository.JwtTokenRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthServiceImpl  implements  AuthService{
    private final EmployeeRepository repository;
    private final JwtTokenRepo jwtTokenRepo;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeMapper mapper;

    @Override
    public String login(LoginDto loginDto) {
        Optional<Employees> optionalUser = repository.findByLogin(loginDto.getLogin());
        if (optionalUser.isEmpty()) {
            log.error("Invalid username {} or password {}", loginDto.getLogin(), loginDto.getPassword());
            throw new RuntimeException("Invalid username or password");
        }
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getLogin(), loginDto.getPassword())
            );
        } catch (Exception e) {
            log.error("Invalid username {} or password {}", loginDto.getLogin(), loginDto.getPassword());
            throw new RuntimeException("Invalid username or password");
        }
        Employees employee = optionalUser.get();
        jwtTokenRepo.deleteByApiTypeAndUser(loginDto.getApiType(), employee);
        return jwtService.generateToken(employee , loginDto);
    }

    @Override
    public EmployeeResponseDto create(EmployeeRequestDto dto) {

//        Optional.ofNullable(dto)
//                .map(mapper::toEntity)
//                .map(repository::save);
//        return mapper.toEntity(dto);


        Employees employees = new Employees();
        employees.setFirstName(dto.getFirstName());
        employees.setLastName(dto.getLastName());
        employees.setPatronymic(dto.getPatronymic());
        employees.setBirthDay(dto.getBirthDay());
        employees.setAddress(dto.getAddress());
        employees.setEmail(dto.getEmail());
        employees.setPhoneNumber(dto.getPhoneNumber());
        employees.setLogin(dto.getLogin());
        employees.setRole(dto.getRole());
        employees.setPassword(passwordEncoder.encode(dto.getPassword()));
        repository.save(employees);
        EmployeeResponseDto employeeResponseDto = mapper.toDto(employees);
        employeeResponseDto.setLogin(employees.getUsername());
        return employeeResponseDto;
    }
}
