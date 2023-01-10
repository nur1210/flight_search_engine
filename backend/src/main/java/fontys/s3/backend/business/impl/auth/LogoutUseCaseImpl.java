package fontys.s3.backend.business.impl.auth;

import fontys.s3.backend.business.usecase.auth.LogoutUseCase;
import fontys.s3.backend.configuration.security.jwt.JwtUtils;
import fontys.s3.backend.configuration.security.services.RefreshTokenService;
import fontys.s3.backend.persistence.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.broker.SubscriptionRegistry;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@AllArgsConstructor
public class LogoutUseCaseImpl implements LogoutUseCase {

    private final RefreshTokenService refreshTokenService;
    private final JwtUtils jwtUtils;
    private SubscriptionRegistry subscriptionRegistry;

    @Override
    public void logout(HttpServletRequest request) {
        String refreshToken = jwtUtils.getRefreshTokenFromCookie(request);
        var token = refreshTokenService.findByToken(refreshToken);
        token.ifPresent(refreshTokenEntity -> {
            UserEntity user = refreshTokenEntity.getUser();
            refreshTokenService.deleteByUserId(user.getId());
            subscriptionRegistry.unregisterAllSubscriptions(user.getEmail());
        });
    }
}
