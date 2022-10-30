package fontys.s3.backend.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RefreshTokenException extends ResponseStatusException {
    public RefreshTokenException(String errorCause) {
        super(HttpStatus.UNAUTHORIZED, errorCause);
    }
}
