package fontys.s3.backend.business.impl;

import fontys.s3.backend.business.AccessTokenEncoder;
import fontys.s3.backend.business.LoginUseCase;
import fontys.s3.backend.business.exception.InvalidCredentialsException;
import fontys.s3.backend.configuration.security.jwt.JwtUtils;
import fontys.s3.backend.configuration.security.services.UserDetailsImpl;
import fontys.s3.backend.domain.AccessToken;
import fontys.s3.backend.domain.LoginRequest;
import fontys.s3.backend.domain.LoginResponse;
import fontys.s3.backend.persistence.UserRepository;
import fontys.s3.backend.persistence.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LoginUseCaseImpl implements LoginUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenEncoder accessTokenEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        UserEntity user = userRepository.findByEmail(loginRequest.getEmail()).isPresent() ? userRepository.findByEmail(loginRequest.getEmail()).get() : null;
        if (user == null) {
            throw new InvalidCredentialsException();
        }

        if (!matchesPassword(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateAccessToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        return LoginResponse.builder()
                .accessToken(jwt)
                .userId(user.getId())
                .email(user.getEmail())
                .roles(roles)
                .build();
    }

    private boolean matchesPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
    private String generateAccessToken(UserEntity user) {
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
