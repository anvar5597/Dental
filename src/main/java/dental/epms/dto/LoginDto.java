package dental.epms.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class LoginDto {
    private String login;

    private String password;

    @Enumerated(EnumType.STRING)
    private ApiType apiType;
}
