package fontys.s3.backend.business.impl;

import fontys.s3.backend.business.GetUserUseCase;
import fontys.s3.backend.business.exception.UnauthorizedDataAccessException;
import fontys.s3.backend.business.impl.converter.UserConverter;
import fontys.s3.backend.domain.AccessToken;
import fontys.s3.backend.domain.User;
import fontys.s3.backend.persistence.UserRepository;
import fontys.s3.backend.persistence.entity.RoleEnum;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetUserUseCaseImpl implements GetUserUseCase {

    private UserRepository userRepository;
    private AccessToken requestToken;

    @Override
    public Optional<User> getUser(long userId) {
        if (!requestToken.hasRole(RoleEnum.ADMIN.name()) && requestToken.getUserId() != userId) {
            throw new UnauthorizedDataAccessException("USER_ID_NOT_FROM_LOGGED_IN_USER");
        }
        return userRepository.findById(userId).map(UserConverter::convert);
    }
}
