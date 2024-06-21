package dental.serviceCategory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ServiceRequestDto {
    @NotBlank
    private String serviceName;
    @NotNull
    private Integer price;
}
