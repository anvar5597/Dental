/**
 * Author: Anvar Olimov
 * User:user
 * Date:1/2/2025
 * Time:7:45 PM
 */


package dental.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {super(message);}
}
