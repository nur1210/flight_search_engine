package fontys.s3.backend.business.impl.user;

import fontys.s3.backend.business.exception.UnauthorizedDataAccessException;
import fontys.s3.backend.business.usecase.user.DeleteUserUseCase;
import fontys.s3.backend.domain.model.AccessToken;
import fontys.s3.backend.persistence.RefreshTokenRepository;
import fontys.s3.backend.persistence.UserRepository;
import fontys.s3.backend.persistence.entity.RoleEnum;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

@Service
@AllArgsConstructor
public class DeleteUserUseCaseImpl implements DeleteUserUseCase {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AccessToken accessToken;
    @Override
    @Transactional
    public void deleteUser(long userId) {
        if (accessToken.hasRole(RoleEnum.ADMIN.name())) {
            var user = userRepository.findById(userId);
            if (user.isPresent()) {
                refreshTokenRepository.deleteByUser(user.get());
                userRepository.deleteById(userId);
            } else {
                throw new NotFoundException("USER_NOT_FOUND");
            }
        } else {
            throw new UnauthorizedDataAccessException("USER_IS_NOT_ADMIN");
        }
    }
}
