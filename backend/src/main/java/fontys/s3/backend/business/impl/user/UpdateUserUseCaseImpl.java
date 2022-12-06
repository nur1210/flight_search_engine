package fontys.s3.backend.business.impl.user;

import fontys.s3.backend.business.exception.InvalidUserException;
import fontys.s3.backend.business.usecase.user.UpdateUserUseCase;
import fontys.s3.backend.domain.request.UpdateUserRequest;
import fontys.s3.backend.persistence.UserRepository;
import fontys.s3.backend.persistence.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void updateUser(long userId, UpdateUserRequest request) {
        var user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new InvalidUserException("USER_ID_INVALID");
        }

        var userEntity = user.get();
        updateFields(request, userEntity);
    }

    private void updateFields(UpdateUserRequest request, UserEntity user) {
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());

        if (request.getNewPassword() != null && !request.getNewPassword().isEmpty()) {
            if (passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
                user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            } else {
                throw new InvalidUserException("PASSWORD_INVALID");
            }
        }

        userRepository.save(user);
    }
}
