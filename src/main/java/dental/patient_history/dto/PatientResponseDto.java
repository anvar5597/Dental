/**
 * Author: Anvar Olimov
 * User:user
 * Date:6/24/2024
 * Time:4:06 PM
 */


package dental.patient_history.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import dental.client.entity.Gender;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PatientResponseDto {
    private Long id;

    private String empName;
    private String empLName;
    private Long empId;
    private Long clientId;
    private String patientName;
    private String patientLName;
    private String phoneNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate birthDay;
    private Gender gender;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime appointmentTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime endTime;

    private List<TServiceListDto> teethServiceEntities;

    private Integer expense;

    private Boolean isServiced ;
}
