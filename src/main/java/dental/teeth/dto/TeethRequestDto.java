package dental.teeth.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeethRequestDto {

    @NotBlank
    private String teethName;
}
