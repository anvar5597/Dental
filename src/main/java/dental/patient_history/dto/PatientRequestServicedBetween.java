/**
 * Author: Anvar Olimov
 * User:Anvar
 * Date:6/15/2025
 * Time:11:13 PM
 */


package dental.patient_history.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PatientRequestServicedBetween {
    LocalDateTime from;
    LocalDateTime to;
}
