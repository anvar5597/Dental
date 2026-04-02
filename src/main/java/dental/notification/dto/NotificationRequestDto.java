/**
 * Author: Anvar Olimov
 * User:user
 * Date:7/16/2024
 * Time:12:41 PM
 */


package dental.notification.dto;


import lombok.Data;


import java.time.LocalDateTime;

@Data
public class NotificationRequestDto {
    private Long clientId;
    private LocalDateTime nextVisit;
}
