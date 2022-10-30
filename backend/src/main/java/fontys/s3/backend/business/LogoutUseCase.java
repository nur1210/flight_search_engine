package fontys.s3.backend.business;

import javax.servlet.http.HttpServletRequest;

public interface LogoutUseCase {
    void logout(HttpServletRequest request);
}
