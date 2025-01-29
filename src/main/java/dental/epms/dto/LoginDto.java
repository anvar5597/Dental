package dental.epms.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {

    @NotBlank(message = "Bu maydon bo'sh bo'lmasligi kerak")
    private String login;

    @NotBlank(message = "Bu maydon bo'sh bo'lmasligi kerak")
    private String password;

    @Enumerated(EnumType.STRING)
    private ApiType apiType;
}
