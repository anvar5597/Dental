/**
 * Author: Anvar Olimov
 * User:user
 * Date:6/24/2024
 * Time:4:03 PM
 */


package dental.patientHistory.dto;

import dental.client.entity.Client;
import dental.epms.entity.Employees;
import dental.patientHistory.entity.TeethServiceEntity;
import dental.serviceCategory.entity.ServiceEntity;
import dental.teeth.entity.TeethEntity;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PatientRequestDto {

    private Long employeeId;

    private Long clientId;

    private LocalDate createdAt;

    private List<TeethServiceDto> teethServiceDtoList;

    private Boolean isServiced ;
}
