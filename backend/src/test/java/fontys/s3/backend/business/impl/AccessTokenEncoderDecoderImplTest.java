package fontys.s3.backend.business.impl;

import fontys.s3.backend.business.exception.InvalidAccessTokenException;
import fontys.s3.backend.business.impl.token.AccessTokenEncoderDecoderImpl;
import fontys.s3.backend.domain.model.AccessToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccessTokenEncoderDecoderImplTest {
    @Mock
    private AccessTokenEncoderDecoderImpl accessTokenEncoderDecoder;

    @SpyBean
    private AccessTokenEncoderDecoderImpl realAccessTokenEncoderDecoder =
            new AccessTokenEncoderDecoderImpl(
                    "E91E158E4C6656F68B1B5D1C316766DE98D2AD6EF3BFB44F78E9CFCDF5");

    @Mock
    private AccessToken accessToken;

    @BeforeEach
    void setUp() {
        accessToken = AccessToken.builder()
                        .subject("subject")
                        .roles(List.of("USER", "ADMIN"))
                        .userId(1L)
                        .build();
    }

    @Test
    void decodeWhenAccessTokenIsInvalidThenThrowException() {
        String accessTokenEncoded =
                "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjY0NjQ3NywiaWF0IjoxNTkyNjQyODc3fQ.X-_q-7x8_2-_q-7x8_2-_q-7x8_2-_q-7x8_2-_q-7x8_2-_q-7x8_2-_q-7x8_2-_q-7x8_2-_q-7x8_2-_q-7x8_2-_q-7x8_2-_q-7x8_2-_q-7x8_2";

        assertThrows(
                InvalidAccessTokenException.class,
                () -> realAccessTokenEncoderDecoder.decode(accessTokenEncoded));
    }

    @Test
    void decodeWhenAccessTokenIsValidThenReturnAccessToken() {
        String accessTokenEncoded = realAccessTokenEncoderDecoder.encode(accessToken);
        AccessToken accessToken = realAccessTokenEncoderDecoder.decode(accessTokenEncoded);

        assertEquals("subject", accessToken.getSubject());
        assertEquals(1, accessToken.getUserId());
        assertEquals(2, accessToken.getRoles().size());
        assertEquals("USER", accessToken.getRoles().get(0));
        assertEquals("ADMIN", accessToken.getRoles().get(1));
    }

    @Test
    @DisplayName("Should return a valid access token when the access token is valid")
    void encodeWhenAccessTokenIsValidThenReturnValidAccessToken() {

        String accessTokenEncoded = realAccessTokenEncoderDecoder.encode(accessToken);

        assertNotNull(accessTokenEncoded);
        assertEquals(accessToken, realAccessTokenEncoderDecoder.decode(accessTokenEncoded));
    }

    @Test
    @DisplayName("Should throw an exception when the access token is invalid")
    void encodeWhenAccessTokenIsInvalidThenThrowException() {

        when(accessTokenEncoderDecoder.encode(accessToken))
                .thenThrow(new InvalidAccessTokenException("INVALID_ACCESS_TOKEN"));

        assertThrows(
                InvalidAccessTokenException.class,
                () -> accessTokenEncoderDecoder.encode(accessToken));
    }
}