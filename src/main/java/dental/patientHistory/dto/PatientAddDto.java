/**
 * Author: Anvar Olimov
 * User:user
 * Date:7/4/2024
 * Time:11:26 AM
 */


package dental.patientHistory.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientAddDto {
    private Long employeeId;

    private Long clientId;

    private LocalDate createdAt;
}
