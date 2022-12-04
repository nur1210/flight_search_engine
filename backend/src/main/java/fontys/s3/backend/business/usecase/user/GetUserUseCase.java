package fontys.s3.backend.business.usecase.user;

import fontys.s3.backend.domain.model.User;

import java.util.Optional;

public interface GetUserUseCase {
    Optional<User> getUser(long userId);
}
