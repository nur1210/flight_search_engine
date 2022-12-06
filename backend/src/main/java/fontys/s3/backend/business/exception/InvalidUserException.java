package fontys.s3.backend.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidUserException extends ResponseStatusException {
    public InvalidUserException() {
        super(HttpStatus.BAD_REQUEST, "INVALID_USER");
    }

    public InvalidUserException(String errorCode) {
        super(HttpStatus.BAD_REQUEST, errorCode);
    }
}
