/**
 * Author: Anvar Olimov
 * User:user
 * Date:6/24/2024
 * Time:4:06 PM
 */


package dental.patientHistory.dto;

import dental.client.dto.ClientResponseDto;
import dental.epms.dto.EmployeeResponseDto;
import dental.patientHistory.entity.TeethServiceEntity;
import dental.serviceCategory.dto.ServiceRespondDto;
import dental.teeth.dto.TeethResponseDto;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PatientResponseDto {
    private Long id;

    private EmployeeResponseDto employeeResponseDto;

    private ClientResponseDto clientResponseDto;

    private LocalDate createdAt;

    private List<TeethServiceEntity> teethServiceEntities;

//    private TeethResponseDto teethDto;

    private Boolean isServiced ;
}
