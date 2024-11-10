package dental.epms.service;


import dental.epms.dto.EmployeeRequestDto;

import dental.epms.dto.LoginDto;
import dental.epms.entity.Employees;
import dental.epms.repository.EmployeeRepository;
import dental.epms.repository.JwtTokenRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional

public class AuthServiceImpl  implements  AuthService {
    private final EmployeeRepository repository;
    private final JwtTokenRepo jwtTokenRepo;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<String> login(LoginDto loginDto) {

        var employeee = Optional.ofNullable(loginDto)
                .map(LoginDto::getLogin)
                .map(this::findEmployeeByLogin);

        if(employeee.isEmpty()) return ResponseEntity.status(401).body("Login yoki parol xato");
        var employee = employeee.get();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getLogin(), loginDto.getPassword()));
        }
        catch (Exception e) {
          log.error("Invalid username {} or password {}", loginDto.getLogin(), loginDto.getPassword());
           return ResponseEntity.status(401).body("Login yoki parol xato");
        }

        jwtTokenRepo.deleteByApiTypeAndUser(loginDto.getApiType(), employee);

        return ResponseEntity.ok(jwtService.generateToken(employee, loginDto));
    }

    @Override
    public ResponseEntity<String> create(EmployeeRequestDto dto) {

        Employees employees = new Employees();
        employees.setFirstName(dto.getFirstName());
        employees.setLastName(dto.getLastName());
        employees.setPatronymic(dto.getPatronymic());
        employees.setBirthDay(dto.getBirthDay());
        employees.setAddress(dto.getAddress());
        employees.setEmail(dto.getEmail());
        Optional <Employees> employees1=repository.findByEmail(dto.getEmail());
        if (employees1.isPresent()){
           ResponseEntity.status(400).body("Bunday email mavjud");
        }
        employees.setPhoneNumber(dto.getPhoneNumber());
        Optional <Employees> employees3=repository.findByPhoneNumber(dto.getPhoneNumber());
        if (employees3.isPresent()){
            ResponseEntity.status(400).body("Bunday telefon nomer mavjud");

        }
        employees.setLogin(dto.getLogin());
        Optional <Employees> employees2=repository.findByLogin(dto.getLogin());
        if (employees2.isPresent()){
            ResponseEntity.status(400).body("Bunday foydalanuvchi nomi mavjud");
        }
        employees.setRole(dto.getRole());
        employees.setPassword(passwordEncoder.encode(dto.getPassword()));
        repository.save(employees);
        return ResponseEntity.ok().body("Hodim yaratildi");
    }

    private Employees findEmployeeByLogin(String login)  {
        return Optional.ofNullable(login)
                .flatMap(repository::findByLogin).orElse(null);
    }
}
