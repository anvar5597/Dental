/**
 * Author: Anvar Olimov
 * User:user
 * Date:7/16/2024
 * Time:12:41 PM
 */


package dental.notification.dto;

import dental.client.entity.Client;
import lombok.Data;

import java.time.LocalDate;

@Data
public class NotificationRequestDto {
    private Long clientId;
    private LocalDate nextVisit;
}
