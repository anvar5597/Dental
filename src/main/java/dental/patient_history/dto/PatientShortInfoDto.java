/**
 * Author: Anvar Olimov
 * User:user
 * Date:10/24/2024
 * Time:10:16 AM
 */


package dental.patient_history.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import dental.client.entity.Gender;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PatientShortInfoDto {

    private Long id;
    private String patientName;
    private String patientLName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate birthday;

    private Gender gender;

    private List<TServiceListDto> teethServiceEntities ;

    private Boolean isServiced;

}
