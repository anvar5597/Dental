package dental.service_category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ServiceRespondDto {

    @NotBlank
    private Long id;
    @NotBlank
    private String serviceName;
    @NotNull
    private Integer price;
    @NotNull
    private Integer expense;
}
