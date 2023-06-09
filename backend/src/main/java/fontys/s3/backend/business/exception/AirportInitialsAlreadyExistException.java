package fontys.s3.backend.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AirportInitialsAlreadyExistException extends ResponseStatusException {
    public AirportInitialsAlreadyExistException() {
        super(HttpStatus.BAD_REQUEST, "AIRPORT_INITIALS_ALREADY_EXISTS");
    }
}
