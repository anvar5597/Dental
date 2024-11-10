/**
 * Author: Anvar Olimov
 * User:user
 * Date:7/29/2024
 * Time:10:52 AM
 */



package dental.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GeneralException extends RuntimeException{

    private final HttpStatus httpStatus;

    public GeneralException(HttpStatus status, String message) {
        super(message);
        this.httpStatus = status;
    }

    public static GeneralException getBadRequestException(String message) {
        return new GeneralException(HttpStatus.BAD_REQUEST, message);
    }

    public static GeneralException getNotFoundException(String message) {
        return new GeneralException(HttpStatus.NOT_FOUND, message);
    }
}