package fontys.s3.backend.business.usecase.auth;

import fontys.s3.backend.domain.request.LoginRequest;
import fontys.s3.backend.domain.response.LoginResponse;

public interface LoginUseCase {
LoginResponse login(LoginRequest loginRequest);
}
