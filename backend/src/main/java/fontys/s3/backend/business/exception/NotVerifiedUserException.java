package fontys.s3.backend.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotVerifiedUserException extends ResponseStatusException {
    public NotVerifiedUserException() {
        super(HttpStatus.UNAUTHORIZED, "NOT_VERIFIED_USER");
    }
}
