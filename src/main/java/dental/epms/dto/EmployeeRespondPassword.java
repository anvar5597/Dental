/**
 * Author: Anvar Olimov
 * User:Anvar
 * Date:6/11/2025
 * Time:9:16 AM
 */


package dental.epms.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmployeeRespondPassword {
    @NotBlank
    private  Long id;
    @NotBlank
    private String login;
    @NotBlank
    private String password;

}
