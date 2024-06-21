package dental.epms.dto;

import dental.epms.entity.ERole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class EmployeeResponseDto {
    @NotNull
    private Long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String patronymic;
    @NotBlank
    private String login;
    @NotBlank
    private String password;
    @NotBlank
    private String email;
    @NotBlank
    private ERole role;
    @NotBlank
    private LocalDate birthDay;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String address;
}
