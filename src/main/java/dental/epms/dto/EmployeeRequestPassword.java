/**
 * Author: Anvar Olimov
 * User:Anvar
 * Date:6/11/2025
 * Time:9:38 AM
 */


package dental.epms.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmployeeRequestPassword {
    @NotBlank
    private String login;
    @NotBlank
    private String password;
}
