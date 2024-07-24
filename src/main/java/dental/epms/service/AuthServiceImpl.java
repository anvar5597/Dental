package dental.epms.service;


import dental.epms.dto.EmployeeRequestDto;

import dental.epms.dto.LoginDto;
import dental.epms.entity.Employees;
import dental.epms.repository.EmployeeRepository;
import dental.epms.repository.JwtTokenRepo;
import dental.utils.DefaultResponseDto;
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

    @Override
    public String login(LoginDto loginDto) {
        Optional<Employees> optionalUser = repository.findByLogin(loginDto.getLogin());
        if (optionalUser.isEmpty()) {
         /*   log.error("Invalid username {} or password {}", loginDto.getLogin(), loginDto.getPassword());
            throw new RuntimeException("Invalid username or password");*/
          return "Login yoki parol noto`g`ri";
        }
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getLogin(), loginDto.getPassword())
            );
        } catch (Exception e) {
        /*    log.error("Invalid username {} or password {}", loginDto.getLogin(), loginDto.getPassword());
            throw new RuntimeException("Invalid username or password");*/
            return "Login yoki parol noto`g`ri";
        }
        Employees employee = optionalUser.get();
        jwtTokenRepo.deleteByApiTypeAndUser(loginDto.getApiType(), employee);
        return jwtService.generateToken(employee , loginDto);
    }

    @Override
    public DefaultResponseDto create(EmployeeRequestDto dto) {

        Employees employees = new Employees();
        employees.setFirstName(dto.getFirstName());
        employees.setLastName(dto.getLastName());
        employees.setPatronymic(dto.getPatronymic());
        employees.setBirthDay(dto.getBirthDay());
        employees.setAddress(dto.getAddress());
        employees.setEmail(dto.getEmail());
        Optional <Employees> employees1=repository.findByEmail(dto.getEmail());
        if (employees1.isPresent()){
            return DefaultResponseDto.builder()
                    .status(400)
                    .message("Bunday email mavjud").build();
        }
        employees.setPhoneNumber(dto.getPhoneNumber());
        Optional <Employees> employees3=repository.findByPhoneNumber(dto.getPhoneNumber());
        if (employees3.isPresent()){
            return DefaultResponseDto.builder()
                    .status(400)
                    .message("Bunday telefon nomer mavjud").build();
        }
        employees.setLogin(dto.getLogin());
        Optional <Employees> employees2=repository.findByLogin(dto.getLogin());
        if (employees2.isPresent()){
            return DefaultResponseDto.builder()
                    .status(400)
                    .message("Bunday foydalanuvchi nomi mavjud").build();
        }
        employees.setRole(dto.getRole());
        employees.setPassword(passwordEncoder.encode(dto.getPassword()));
        repository.save(employees);

        return DefaultResponseDto.builder()
                .status(200)
                .message("Hodim yaratildi")
                .build();
    }
}
