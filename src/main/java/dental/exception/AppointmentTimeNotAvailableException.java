/**
 * Author: Anvar Olimov
 * User:user
 * Date:8/2/2024
 * Time:4:50 PM
 */


package dental.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class    AppointmentTimeNotAvailableException extends RuntimeException{
    public AppointmentTimeNotAvailableException(String message) {
        super(message);
    }
}
