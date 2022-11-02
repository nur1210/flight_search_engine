package fontys.s3.backend.business;

import fontys.s3.backend.domain.User;

import java.util.List;

public interface GetAllUsersUseCase {
    List<User> getAllUsers();
}
