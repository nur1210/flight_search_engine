package fontys.s3.backend.business.usecase.user;

import fontys.s3.backend.domain.request.CreateUserRequest;
import fontys.s3.backend.domain.response.CreateUserResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;

public interface CreateUserUseCase {
    CompletableFuture<CreateUserResponse> createUser(CreateUserRequest request, HttpServletRequest httpServletRequest);
}
