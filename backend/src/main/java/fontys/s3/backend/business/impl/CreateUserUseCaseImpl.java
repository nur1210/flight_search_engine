package fontys.s3.backend.business.impl;

import fontys.s3.backend.business.CreateUserUseCase;
import fontys.s3.backend.business.exception.InvalidCredentialsException;
import fontys.s3.backend.domain.CreateUserRequest;
import fontys.s3.backend.domain.CreateUserResponse;
import fontys.s3.backend.persistence.UserRepository;
import fontys.s3.backend.persistence.entity.RoleEnum;
import fontys.s3.backend.persistence.entity.UserEntity;
import fontys.s3.backend.persistence.entity.UserRoleEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class CreateUserUseCaseImpl implements CreateUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public CreateUserResponse createUser(CreateUserRequest request) {
        if (Boolean.TRUE.equals(userRepository.existsByEmail(request.getEmail()))) {
            throw new InvalidCredentialsException();
        }
        UserEntity saveUser = saveNewUser(request);
        return CreateUserResponse.builder()
                .userId(saveUser.getId())
                .build();
    }

    UserEntity saveNewUser(CreateUserRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        UserEntity newUser = UserEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(encodedPassword)
                .build();

        newUser.setUserRoles(Set.of(
                UserRoleEntity.builder()
                        .user(newUser)
                        .role(RoleEnum.USER)
                        .build()));
        return userRepository.save(newUser);

    }
}