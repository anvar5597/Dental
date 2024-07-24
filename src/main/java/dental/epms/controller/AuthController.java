package dental.epms.controller;


import dental.epms.dto.EmployeeRequestDto;
import dental.epms.dto.EmployeeResponseDto;
import dental.epms.dto.LoginDto;
import dental.epms.service.AuthService;
import dental.utils.DefaultResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    private ResponseEntity<String> login(@RequestBody LoginDto loginDto){
        return ResponseEntity.ok(authService.login(loginDto));
    }

    @PostMapping("/create")
    DefaultResponseDto create(@RequestBody EmployeeRequestDto entity){
        return authService.create(entity);
    }


}
