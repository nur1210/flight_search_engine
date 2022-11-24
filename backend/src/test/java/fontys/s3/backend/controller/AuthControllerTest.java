package fontys.s3.backend.controller;

import fontys.s3.backend.business.LoginUseCase;
import fontys.s3.backend.business.LogoutUseCase;
import fontys.s3.backend.business.RefreshTokenUseCase;
import fontys.s3.backend.configuration.security.jwt.JwtUtils;
import fontys.s3.backend.configuration.security.services.RefreshTokenService;
import fontys.s3.backend.domain.LoginRequest;
import fontys.s3.backend.domain.LoginResponse;
import fontys.s3.backend.persistence.entity.RefreshTokenEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private AuthController authController;

    @MockBean
    private RefreshTokenUseCase refreshTokenUseCase;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private LoginUseCase loginUseCase;

    @Mock
    private LogoutUseCase logoutUseCase;

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private ResponseCookie cookie;

    @MockBean
    private JwtUtils jwtUtils;

    @Test
    @WithAnonymousUser
    void login() throws Exception {

        LoginRequest request = LoginRequest.builder()
                .email("a@gmail.com")
                .password("1234")
                .build();

        LoginResponse loginResponse = LoginResponse.builder()
                .userId(1L)
                .email("a@gmail.com")
                .roles(List.of("USER", "ADMIN"))
                .accessToken(null)
                .build();

        RefreshTokenEntity refreshToken = RefreshTokenEntity
                .builder()
                .id(1L)
                .token("refreshToken")
                .build();

        cookie = ResponseCookie
                .from("refreshToken", "refreshToken")
                .httpOnly(true)
                .path("/")
                .maxAge(60 * 60 * 24 * 7)
                .build();

        when(loginUseCase.login(any(LoginRequest.class))).thenReturn(loginResponse);

        when(refreshTokenService.createRefreshToken(request.getEmail())).thenReturn(refreshToken);

        when(jwtUtils.generateRefreshTokenCookie(anyString())).thenReturn(cookie);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "email": "a@gmail.com",
                                    "password": "1234"
                                }
                                """))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(cookie().exists("refreshToken"))
                .andExpect(content().json("""
                        {
                            "accessToken": null,
                                "userId": 1,
                                "email": "a@gmail.com",
                                "roles": [
                                    "USER",
                                    "ADMIN"
                                ]
                           }"""));
    }

    @Test
    @WithMockUser(username = "a@gmail.com", password = "1234", roles = {"USER", "ADMIN"})
    void logout() throws Exception {

        cookie = ResponseCookie
                .from("refreshToken", null)
                .httpOnly(true)
                .path("/")
                .maxAge(60 * 60 * 24 * 7)
                .build();
        doCallRealMethod().when(jwtUtils).getCleanRefreshTokenCookie();
        doNothing().when(logoutUseCase).logout(any());

        mockMvc.perform(post("/auth/logout"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(cookie().value("refreshToken", (String) null));
    }


    @Test
    void refreshToken() throws Exception {
    }

    @Test
    void testRefreshToken() throws Exception {
        when(refreshTokenUseCase.refreshAccessToken(any())).thenReturn("ABC123");
        when(jwtUtils.getRefreshTokenFromCookie(any())).thenReturn("ABC123");
        when(jwtUtils.generateRefreshTokenCookie(any())).thenReturn(null);
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders
                .formLogin();
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(authController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }


}