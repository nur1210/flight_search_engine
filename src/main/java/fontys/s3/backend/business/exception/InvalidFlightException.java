package fontys.s3.backend.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidFlightException extends ResponseStatusException {
    public InvalidFlightException(String errorCode) {
        super(HttpStatus.BAD_REQUEST, errorCode);
    }
}