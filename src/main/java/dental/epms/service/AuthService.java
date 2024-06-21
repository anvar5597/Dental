package dental.epms.service;

import dental.epms.dto.EmployeeRequestDto;
import dental.epms.dto.EmployeeResponseDto;
import dental.epms.dto.LoginDto;

public interface AuthService {
    public String login(LoginDto loginDto);

    EmployeeResponseDto create(EmployeeRequestDto dto);
}
