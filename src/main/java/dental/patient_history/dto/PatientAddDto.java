/**
 * Author: Anvar Olimov
 * User:user
 * Date:7/4/2024
 * Time:11:26 AM
 */


package dental.patient_history.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class PatientAddDto {
    private Long employeeId;

    private Long clientId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime appointmentTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endTime;

}
