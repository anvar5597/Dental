/**
 * Author: Anvar Olimov
 * User:user
 * Date:7/4/2024
 * Time:11:51 AM
 */


package dental.patientHistory.dto;


import lombok.Data;



@Data
public class PatientServiceAddDto {

    private Long patientId;
    private Long serviceId;
    private Long teethId;
}
