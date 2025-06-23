/**
 * Author: Anvar Olimov
 * User:user
 * Date:5/4/2025
 * Time:9:58 PM
 */


package dental.client.analys.dto;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class AnalyseDto {

    @Id
    private Long id;

    private String fileName;
}
