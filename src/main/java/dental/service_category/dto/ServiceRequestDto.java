package dental.service_category.dto;

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
