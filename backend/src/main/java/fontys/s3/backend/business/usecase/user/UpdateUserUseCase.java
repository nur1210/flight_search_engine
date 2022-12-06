package fontys.s3.backend.business.usecase.user;

import fontys.s3.backend.domain.request.UpdateUserRequest;

public interface UpdateUserUseCase {
    void updateUser(long userId, UpdateUserRequest request);
}
