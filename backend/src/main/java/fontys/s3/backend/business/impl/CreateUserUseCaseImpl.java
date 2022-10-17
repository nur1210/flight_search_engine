package fontys.s3.backend.business.impl;

import fontys.s3.backend.business.CreateUserUseCase;
import fontys.s3.backend.domain.CreateUserRequest;
import fontys.s3.backend.domain.CreateUserResponse;
import fontys.s3.backend.persistence.UserRepository;
import fontys.s3.backend.persistence.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateUserUseCaseImpl implements CreateUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public CreateUserResponse createUser(CreateUserRequest request) {

        UserEntity saveUser = saveNewUser(request);
        return CreateUserResponse.builder()
                .userId(saveUser.getId())
                .build();
    }

    private UserEntity saveNewUser(CreateUserRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        UserEntity newUser = UserEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(encodedPassword)
                .build();

        return userRepository.save(newUser);
/*        newUser.setUserRoles(Set.of(
                UserRole.builder()
                        .user(newUser)
                        .role(RoleEnum.STUDENT)
}*/
    }
}