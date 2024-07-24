/**
 * Author: Anvar Olimov
 * User:user
 * Date:6/21/2024
 * Time:11:06 AM
 */


package dental.client.dto;

import dental.client.entity.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ClientResponseDto {
    private Long id;

    private String name;

    private String  lastName;

    private String patronymic;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate birthday;

    private String phoneNumber;

    private String address;
}
