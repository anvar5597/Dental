package dental.epms.controller;


import dental.epms.dto.*;
import dental.epms.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
@Validated
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    ResponseEntity<AuthDto> login(@Valid @RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authService.login(loginDto));
    }

    @PostMapping("/create")
    ResponseEntity<String> create(@RequestBody EmployeeRequestDto entity) {
        return authService.create(entity);
    }

    @PutMapping("/update-password/{id}")
    ResponseEntity<String> updatePassword(@RequestBody EmployeeRequestPassword respondPassword, @PathVariable Long id) {
        return ResponseEntity.ok(authService.updatePassword(respondPassword,id));
    }

}
