package dental.epms.service;

import dental.epms.dto.*;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    AuthDto login(LoginDto loginDto);

    ResponseEntity<String> create(EmployeeRequestDto dto);
    String updatePassword(EmployeeRequestPassword dto, Long id);
    String delete(Long id);

}
