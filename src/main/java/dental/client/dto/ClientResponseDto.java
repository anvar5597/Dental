/**
 * Author: Anvar Olimov
 * User:user
 * Date:6/21/2024
 * Time:11:06 AM
 */


package dental.client.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import dental.client.entity.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class ClientResponseDto implements Serializable {
    private Long id;

    private String name;

    private String  lastName;

    private String patronymic;

    private String comment;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @JsonFormat(shape = JsonFormat.Shape.STRING  , pattern = "dd/MM/yyyy")
    private LocalDate birthday;

    private String phoneNumber;

    private String address;
}
