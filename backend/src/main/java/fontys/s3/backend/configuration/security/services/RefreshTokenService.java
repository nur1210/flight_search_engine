package fontys.s3.backend.configuration.security.services;

import fontys.s3.backend.business.exception.RefreshTokenException;
import fontys.s3.backend.configuration.security.jwt.JwtUtils;
import fontys.s3.backend.persistence.RefreshTokenRepository;
import fontys.s3.backend.persistence.UserRepository;
import fontys.s3.backend.persistence.entity.RefreshTokenEntity;
import fontys.s3.backend.persistence.entity.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
public class RefreshTokenService {
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private UserRepository userRepository;
    private final Key key;
    @Autowired
    private JwtUtils jwtUtils;

    public RefreshTokenService(@Value("${jwt.secret}") String jwtSecret) {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    public Optional<RefreshTokenEntity> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshTokenEntity createRefreshToken(String email) {
        Instant now = Instant.now();
        String token = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(+30, ChronoUnit.DAYS)))
                .signWith(key)
                .compact();

        RefreshTokenEntity refreshToken = new RefreshTokenEntity();
        Optional<UserEntity> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            refreshToken.setUser(user.get());
            refreshToken.setExpiryDate(Instant.from(now.plus(+30, ChronoUnit.DAYS)));
            refreshToken.setToken(token);

            refreshToken = refreshTokenRepository.save(refreshToken);
            return refreshToken;
        } else {
            return null;
        }
    }

    public RefreshTokenEntity verifyExpiration(RefreshTokenEntity token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RefreshTokenException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    @Transactional
    public void deleteByUserId(Long userId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        user.ifPresent(userEntity -> refreshTokenRepository.deleteByUser(userEntity));
    }
}
