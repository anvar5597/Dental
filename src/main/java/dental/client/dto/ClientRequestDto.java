/**
 * Author: Anvar Olimov
 * User:user
 * Date:6/21/2024
 * Time:10:51 AM
 */


package dental.client.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClientRequestDto {

    private String name;

    private String  lastName;

    private String patronymic;

    private LocalDate birthday;

    private String phoneNumber;

    private String address;
}
