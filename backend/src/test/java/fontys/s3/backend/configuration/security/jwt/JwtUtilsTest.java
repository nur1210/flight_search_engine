package fontys.s3.backend.configuration.security.jwt;

import fontys.s3.backend.persistence.entity.PriceAlertEntity;
import fontys.s3.backend.persistence.entity.UserEntity;
import fontys.s3.backend.persistence.entity.UserRoleEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseCookie;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class JwtUtilsTest {
    @MockBean
    private JwtUtils jwtUtils = mock(JwtUtils.class);

    @Test
    void testGenerateAccessToken() {

        when(jwtUtils.generateAccessToken(any())).thenReturn("ABC123");
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFirstName("Jane");
        userEntity.setLastName("Doe");
        userEntity.setPassword("iloveyou");
        ArrayList<PriceAlertEntity> priceAlerts = new ArrayList<>();
        userEntity.setPriceAlerts(priceAlerts);
        HashSet<UserRoleEntity> userRoles = new HashSet<>();
        userEntity.setUserRoles(userRoles);

        String actualGenerateAccessTokenResult = jwtUtils.generateAccessToken(userEntity);

        assertEquals("ABC123", actualGenerateAccessTokenResult);
        verify(jwtUtils).generateAccessToken(any());
    }


    @Test
    void testGenerateRefreshTokenCookie() {

        when(jwtUtils.generateRefreshTokenCookie(any())).thenReturn(null);
        String refreshToken = "ABC123";

        ResponseCookie actualGenerateRefreshTokenCookieResult = jwtUtils.generateRefreshTokenCookie(refreshToken);

        assertNull(actualGenerateRefreshTokenCookieResult);
        verify(jwtUtils).generateRefreshTokenCookie(any());
    }


    @Test
    void testGetRefreshTokenFromCookie() {

        when(jwtUtils.getRefreshTokenFromCookie(any())).thenReturn("ABC123");
        MockHttpServletRequest request = new MockHttpServletRequest();

        String actualRefreshTokenFromCookie = jwtUtils.getRefreshTokenFromCookie(request);

        assertEquals("ABC123", actualRefreshTokenFromCookie);
        verify(jwtUtils).getRefreshTokenFromCookie(any());
    }


    @Test
    void testGetCleanRefreshTokenCookie() {

        when(jwtUtils.getCleanRefreshTokenCookie()).thenReturn(null);

        ResponseCookie actualCleanRefreshTokenCookie = jwtUtils.getCleanRefreshTokenCookie();

        assertNull(actualCleanRefreshTokenCookie);
        verify(jwtUtils).getCleanRefreshTokenCookie();
    }


    @Test
    void testGetUserEmailFromJwtToken() {

        String token = "ABC123";
        when(jwtUtils.getUserEmailFromJwtToken(token)).thenReturn("test@gmail.com");

        String actualUserEmailFromJwtToken = jwtUtils.getUserEmailFromJwtToken(token);

        assertEquals("test@gmail.com", actualUserEmailFromJwtToken);
        verify(jwtUtils).getUserEmailFromJwtToken(token);
    }


    @Test
    void testValidateJwtTokenReturnTrue() {

        when(jwtUtils.validateJwtToken(any())).thenReturn(true);
        String authToken = "ABC123";

        boolean actualValidateJwtTokenResult = jwtUtils.validateJwtToken(authToken);

        assertTrue(actualValidateJwtTokenResult);
        verify(jwtUtils).validateJwtToken(any());
    }


    @Test
    void testValidateJwtTokenReturnFalse() {

        when(jwtUtils.validateJwtToken(any())).thenReturn(false);
        String authToken = "ABC123";

        boolean actualValidateJwtTokenResult = jwtUtils.validateJwtToken(authToken);

        assertFalse(actualValidateJwtTokenResult);
        verify(jwtUtils).validateJwtToken(any());
    }
}

