package fontys.s3.backend.configuration.security.jwt;

import fontys.s3.backend.configuration.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    private final Key key;

    @Value("${refreshCookieName}")
    private String refreshCookieName;

    public JwtUtils(@Value("${jwt.secret}") String jwtSecret) {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        Map<String, Object> claimsMap = new HashMap<>();
        if (!roles.isEmpty()) {
            claimsMap.put("roles", roles);
        }

        if (userPrincipal.getId() != null) {
            claimsMap.put("userId", userPrincipal.getId());
        }

        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(+30, ChronoUnit.MINUTES)))
                .addClaims(claimsMap)
                .signWith(key)
                .compact();
    }

    public ResponseCookie generateRefreshTokenCookie(String refreshToken) {
        return generateCookie(refreshCookieName, refreshToken, "auth/refresh");
    }

    public String getRefreshTokenFromCookie(HttpServletRequest request) {
        return getCookieValueByName(request, refreshCookieName);
    }

    public ResponseCookie getCleanRefreshTokenCookie() {
        return ResponseCookie.from(refreshCookieName, null)
                .path("auth/refresh")
                .build();
    }

    public String getUserEmailFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    private ResponseCookie generateCookie(String cookieName, String cookieValue, String path) {
        return ResponseCookie.from(cookieName, cookieValue)
                .path(path)
                .maxAge(7 * 24 * 60 * 60)
                .httpOnly(true)
                .build();
    }

    private String getCookieValueByName(HttpServletRequest request, String cookieName) {
        Cookie cookie = WebUtils.getCookie(request, cookieName);
        if (cookie != null) {
            return cookie.getValue();
        }
        return null;
    }
}
