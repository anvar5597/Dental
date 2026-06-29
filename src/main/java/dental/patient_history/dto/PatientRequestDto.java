/**
 * Author: Anvar Olimov
 * User:user
 * Date:6/24/2024
 * Time:4:03 PM
 */


package dental.patient_history.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class PatientRequestDto {

    private Long employeeId;

    private Long clientId;

    private LocalDateTime createdAt;

    private List<TeethServiceDto> teethServiceDtoList;

    private Boolean isServiced ;
}
