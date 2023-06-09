package fontys.s3.backend.business.impl.user;

import fontys.s3.backend.business.exception.UnauthorizedDataAccessException;
import fontys.s3.backend.business.impl.converter.UserConverter;
import fontys.s3.backend.business.usecase.user.GetAllUsersUseCase;
import fontys.s3.backend.domain.model.AccessToken;
import fontys.s3.backend.domain.model.User;
import fontys.s3.backend.persistence.UserRepository;
import fontys.s3.backend.persistence.entity.RoleEnum;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class GetAllUsersUseCaseImpl implements GetAllUsersUseCase {
    private UserRepository userRepository;
    private AccessToken requestToken;


    @Override
    public List<User> getAllUsers() {
        if (requestToken.hasRole(RoleEnum.ADMIN.name())) {
            return userRepository.findAll()
                    .stream()
                    .map(UserConverter::convert)
                    .toList();
        }
        throw new UnauthorizedDataAccessException("USER_IS_NOT_ADMIN");
    }
}
