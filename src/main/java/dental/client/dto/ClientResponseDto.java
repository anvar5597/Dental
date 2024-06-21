/**
 * Author: Anvar Olimov
 * User:user
 * Date:6/21/2024
 * Time:11:06 AM
 */


package dental.client.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClientResponseDto {
    private Long id;

    private String name;

    private String  lastName;

    private String patronymic;

    private LocalDate birthday;

    private String phoneNumber;

    private String address;
}
