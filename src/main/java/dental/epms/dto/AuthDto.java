/**
 * Author: Anvar Olimov
 * User:user
 * Date:1/2/2025
 * Time:7:40 PM
 */


package dental.epms.dto;

import dental.epms.entity.ERole;
import lombok.Data;


@Data
public class AuthDto {

    private Long id;
    private String lastName;

    private String firstName;

    private ERole role;

    private String token;

}
