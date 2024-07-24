package dental.epms.service;

import dental.epms.dto.EmployeeRequestDto;
import dental.epms.dto.EmployeeResponseDto;
import dental.epms.dto.LoginDto;
import dental.utils.DefaultResponseDto;

public interface AuthService {
    public String login(LoginDto loginDto);

    DefaultResponseDto create(EmployeeRequestDto dto);
}
