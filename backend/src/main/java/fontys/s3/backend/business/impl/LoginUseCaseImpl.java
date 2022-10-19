package fontys.s3.backend.business.impl;

import fontys.s3.backend.business.AccessTokenEncoder;
import fontys.s3.backend.business.LoginUseCase;
import fontys.s3.backend.business.exception.InvalidCredentialsException;
import fontys.s3.backend.domain.AccessToken;
import fontys.s3.backend.domain.LoginRequest;
import fontys.s3.backend.domain.LoginResponse;
import fontys.s3.backend.persistence.UserRepository;
import fontys.s3.backend.persistence.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LoginUseCaseImpl implements LoginUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenEncoder accessTokenEncoder;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        UserEntity user = userRepository.findByEmail(loginRequest.getEmail());
        if (user == null) {
            throw new InvalidCredentialsException();
        }

        if (!matchesPassword(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        Map<String, String> accessTokens = generateTokens(user);

        return LoginResponse.builder()
                .tokens(accessTokens)
                .build();
    }

    private boolean matchesPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
    private Map<String, String> generateTokens(UserEntity user) {
        Long userId = user != null ? user.getId() : null;
        List<String> roles = user.getUserRoles().stream()
                .map(userRole -> userRole.getRole().toString())
                .toList();

        return accessTokenEncoder.encode(
                AccessToken.builder()
                        .subject(Objects.requireNonNull(user).getEmail())
                        .roles(roles)
                        .userId(userId)
                        .build());
    }
}
