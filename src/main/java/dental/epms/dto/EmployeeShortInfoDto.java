/**
 * Author: Anvar Olimov
 * User:user
 * Date:11/20/2024
 * Time:9:02 AM
 */


package dental.epms.dto;

import dental.epms.entity.ERole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmployeeShortInfoDto {
    @NotNull
    private Long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String patronymic;
    @NotBlank
    private ERole role;
}
