/**
 * Author: Anvar Olimov
 * User:Anvar
 * Date:6/11/2025
 * Time:10:41 PM
 */


package dental.epms.dto;

import dental.epms.entity.ERole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeUpdateRequest {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String patronymic;
    @NotBlank
    @Email(message = "Email noto‘g‘ri kiritilgan")
    private String email;
    @NotBlank
    @Enumerated(EnumType.STRING)
    private ERole role;
    @NotBlank
    private LocalDate birthDay;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String address;

}
