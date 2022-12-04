package fontys.s3.backend.business.usecase.user;

import fontys.s3.backend.domain.model.User;

import java.util.List;

public interface GetAllUsersUseCase {
    List<User> getAllUsers();
}
