package fontys.s3.backend.business;

import fontys.s3.backend.domain.LoginRequest;
import fontys.s3.backend.domain.LoginResponse;

public interface LoginUseCase {
LoginResponse login(LoginRequest loginRequest);
}
