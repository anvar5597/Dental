/**
 * Author: Anvar Olimov
 * User:user
 * Date:7/16/2024
 * Time:1:56 PM
 */


package dental.notification.dto;

import lombok.Data;

import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class NotificationRespondDto {

    private Long id;
    private String clientName;
    private String clientLastName;
    private String phoneNumber;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime nextVisit;
}
