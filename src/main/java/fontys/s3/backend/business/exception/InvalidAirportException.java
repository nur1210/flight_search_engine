package fontys.s3.backend.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidAirportException extends ResponseStatusException {
    public InvalidAirportException() {
        super(HttpStatus.BAD_REQUEST, "AIRPORT_INVALID");
    }

    public InvalidAirportException(String errorCode) {
        super(HttpStatus.BAD_REQUEST, errorCode);
    }
}
