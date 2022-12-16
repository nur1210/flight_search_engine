package fontys.s3.backend.business.impl.user;

import fontys.s3.backend.business.usecase.user.VerifyUserUseCase;
import fontys.s3.backend.persistence.UserRepository;
import fontys.s3.backend.persistence.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VerifyUserUseCaseImpl implements VerifyUserUseCase {
    private final UserRepository userRepository;
    @Override
    public boolean verifyUser(String code) {
        UserEntity user = userRepository.findByVerificationCode(code);

        if (user == null || user.isVerified()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setVerified(true);
            userRepository.save(user);
            return true;
        }
    }
}
