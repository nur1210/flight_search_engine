package fontys.s3.backend.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FlightNumberAlreadyExistsException extends ResponseStatusException {
    public FlightNumberAlreadyExistsException() {
        super(HttpStatus.BAD_REQUEST, "FLIGHT_NUMBER_ALREADY_EXISTS");
    }
}
