package fontys.s3.backend.business.impl;

import fontys.s3.backend.business.RefreshTokenDecoder;
import fontys.s3.backend.business.exception.InvalidAccessTokenException;
import fontys.s3.backend.domain.RefreshToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Service
public class RefreshTokenEncoderDecoderImpl implements RefreshTokenDecoder {
    private final Key key;

    public RefreshTokenEncoderDecoderImpl(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        key = Keys.hmacShaKeyFor(keyBytes);
    }


    @Override
    public RefreshToken decode(String refreshTokenEncoded) {
        try {
            Jwt jwt = Jwts.parserBuilder().setSigningKey(key).build().parse(refreshTokenEncoded);
            Claims claims = (Claims) jwt.getBody();

            long userId = claims.get("userId", Long.class);
            Date expiration = claims.get("exp", Date.class);

            //if token is not expired create new access token
            if(expiration.after(Date.from(Instant.now()))) {
                throw new InvalidAccessTokenException("REFRESH_TOKEN_EXPIRED");
            }


            return RefreshToken.builder()
                    .subject(claims.getSubject())
                    .userId(userId)
                    .expirationDate(expiration)
                    .build();
        } catch (JwtException e) {
            throw new InvalidAccessTokenException("INVALID_REFRESH_TOKEN");
        }
    }
}
