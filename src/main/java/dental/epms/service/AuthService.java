package dental.epms.service;

import dental.epms.dto.AuthDto;
import dental.epms.dto.EmployeeRequestDto;
import dental.epms.dto.LoginDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    AuthDto login(LoginDto loginDto);

    ResponseEntity<String> create(EmployeeRequestDto dto);
}
