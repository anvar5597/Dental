package dental.epms.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeRequestDto {

    @NotBlank
    private String firstName;
    @NotBlank
    private  String lastName;
    @NotBlank
    private String patronymic;
    @NotBlank
    private String userName;
    @NotBlank
    private String password;

    @NotNull(message = "")
    private Integer role;

    @NotNull(message = "")
    private Integer room;
}
