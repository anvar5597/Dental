/**
 * Author: Anvar Olimov
 * User:user
 * Date:7/16/2024
 * Time:3:37 PM
 */


package dental.utils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DefaultResponseDto {

    private Long doctorId;
    private Integer status;
    private String message;
}
