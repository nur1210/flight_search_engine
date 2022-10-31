package fontys.s3.backend.business.impl;

import fontys.s3.backend.business.LogoutUseCase;
import fontys.s3.backend.configuration.security.jwt.JwtUtils;
import fontys.s3.backend.configuration.security.services.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class LogoutUseCaseImpl implements LogoutUseCase {

    private final RefreshTokenService refreshTokenService;
    private final JwtUtils jwtUtils;

    @Override
    public void logout(HttpServletRequest request) {
        String refreshToken = jwtUtils.getRefreshTokenFromCookie(request);
        var token = refreshTokenService.findByToken(refreshToken);
        token.ifPresent(refreshTokenEntity -> refreshTokenService.deleteByUserId(refreshTokenEntity.getUser().getId()));
    }
}
