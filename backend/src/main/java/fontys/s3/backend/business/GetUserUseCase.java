package fontys.s3.backend.business;

import fontys.s3.backend.domain.User;

import java.util.Optional;

public interface GetUserUseCase {
    Optional<User> getUser(long userId);
}
