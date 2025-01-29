/**
 * Author: Anvar Olimov
 * User:user
 * Date:7/16/2024
 * Time:1:56 PM
 */


package dental.notification.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class NotificationRespondDto {

    private Long id;
    private String clientName;
    private String clientLastName;
    private String phoneNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate nextVisit;
}
