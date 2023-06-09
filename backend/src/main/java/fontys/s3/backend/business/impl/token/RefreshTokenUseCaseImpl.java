package fontys.s3.backend.business.impl.token;

import fontys.s3.backend.business.exception.RefreshTokenException;
import fontys.s3.backend.business.usecase.token.RefreshTokenUseCase;
import fontys.s3.backend.configuration.security.jwt.JwtUtils;
import fontys.s3.backend.configuration.security.services.RefreshTokenService;
import fontys.s3.backend.persistence.entity.RefreshTokenEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenUseCaseImpl implements RefreshTokenUseCase {
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Override
    public String refreshAccessToken(String refreshToken) {
        if ((refreshToken != null) && (refreshToken.length() > 0)) {
            return refreshTokenService.findByToken(refreshToken)
                    .map(refreshTokenService::verifyExpiration)
                    .map(RefreshTokenEntity::getUser)
                    .map(jwtUtils::generateAccessToken).get();
        }
        throw new RefreshTokenException(refreshToken, "Refresh token is not in database");
    }
}