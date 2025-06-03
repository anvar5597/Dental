package dental.service_category.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

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
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime createdAt;
}
