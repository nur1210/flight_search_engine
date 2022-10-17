package fontys.s3.backend.business;

import fontys.s3.backend.domain.CreateUserRequest;
import fontys.s3.backend.domain.CreateUserResponse;

public interface CreateUserUseCase {
    CreateUserResponse createUser(CreateUserRequest request);
}
