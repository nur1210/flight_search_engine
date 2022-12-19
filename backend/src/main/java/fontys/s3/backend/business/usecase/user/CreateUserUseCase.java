package fontys.s3.backend.business.usecase.user;

import fontys.s3.backend.domain.request.CreateUserRequest;
import fontys.s3.backend.domain.response.CreateUserResponse;

import javax.servlet.http.HttpServletRequest;

public interface CreateUserUseCase {
    CreateUserResponse createUser(CreateUserRequest request, HttpServletRequest httpServletRequest);
}
