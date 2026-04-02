/**
 * Author: Anvar Olimov
 * User:user
 * Date:7/16/2024
 * Time:12:41 PM
 */


package dental.notification.dto;


import lombok.Data;


import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class NotificationRequestDto {
    private Long clientId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime nextVisit;
}
