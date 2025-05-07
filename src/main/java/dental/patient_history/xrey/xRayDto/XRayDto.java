/**
 * Author: Anvar Olimov
 * User:user
 * Date:5/4/2025
 * Time:10:31 PM
 */


package dental.patient_history.xrey.xRayDto;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class XRayDto {
    @Id
    private Long id;

    private String fileName;
}
