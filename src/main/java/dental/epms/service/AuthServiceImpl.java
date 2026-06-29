package dental.epms.service;


import dental.epms.dto.AuthDto;
import dental.epms.dto.EmployeeRequestDto;
import dental.epms.dto.EmployeeRequestPassword;
import dental.epms.dto.LoginDto;
import dental.epms.entity.Employees;
import dental.epms.entity.JwtTokenEntity;
import dental.epms.repository.EmployeeRepository;
import dental.epms.repository.JwtTokenRepo;
import dental.exception.UnauthorizedException;
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
    private final EmployeeRepository employeeRepository;

    @Override
    public AuthDto login(LoginDto loginDto) {

        var employeee = Optional.ofNullable(loginDto)
                .map(LoginDto::getLogin)
                .map(this::findEmployeeByLogin);

        if(employeee.isEmpty()) throw  new UnauthorizedException("Login yoki parol xato");
        var employee = employeee.get();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getLogin(), loginDto.getPassword()));
        }
        catch (Exception e) {
          log.error("Invalid username {} or password {}", loginDto.getLogin(), loginDto.getPassword());
           throw new UnauthorizedException("Login yoki parol xato");
        }

        jwtTokenRepo.deleteByApiTypeAndUser(loginDto.getApiType(), employee);
        AuthDto authDto=new AuthDto();
        authDto.setId(employee.getId());
        authDto.setLastName(employeee.get().getLastName());
        authDto.setFirstName(employee.getFirstName());
        authDto.setRole(employeee.get().getRole());
        authDto.setToken(jwtService.generateToken(employee, loginDto));

        return authDto;
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
           throw new IllegalArgumentException("Bunday elektron pochta mavjud");
        }
        employees.setPhoneNumber(dto.getPhoneNumber());
        Optional <Employees> employees3=repository.findByPhoneNumber(dto.getPhoneNumber());
        if (employees3.isPresent()){
           throw new IllegalArgumentException("Bunday telefon raqam mavjud");

        }
        employees.setLogin(dto.getLogin());
        Optional <Employees> employees2=repository.findByLogin(dto.getLogin());
        if (employees2.isPresent()){
        throw new IllegalArgumentException("Bunday foydalanuvchi nomi mavjud");
        }
        employees.setRole(dto.getRole());
        employees.setOpenPassword(dto.getPassword());
        employees.setPassword(passwordEncoder.encode(dto.getPassword()));
        repository.save(employees);
        return ResponseEntity.ok().body("Hodim yaratildi");
    }
    @Override
    public String updatePassword(EmployeeRequestPassword dto, Long id) {
        Employees employees = repository.getReferenceById(id);

        employees.setLogin(dto.getLogin());
        employees.setOpenPassword(dto.getPassword());
        employees.setPassword(passwordEncoder.encode(dto.getPassword()));
        repository.save(employees);
        return "Password updated";
    }

    @Override
    public String delete(Long id) {
        Optional<Employees> employeesEntity = employeeRepository.findById(id);
        if (employeesEntity.isEmpty()) throw new IllegalArgumentException("Bunday foydalanuvchi nomi mavjud");
       jwtTokenRepo.deleteByUser(employeesEntity.get());
        return "O`chirildi";
    }


    private Employees findEmployeeByLogin(String login)  {
        return Optional.ofNullable(login)
                .flatMap(repository::findByLogin).orElse(null);
    }
}
