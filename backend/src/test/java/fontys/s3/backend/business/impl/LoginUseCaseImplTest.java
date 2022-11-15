package fontys.s3.backend.business.impl;

import fontys.s3.backend.configuration.security.jwt.JwtUtils;
import fontys.s3.backend.configuration.security.services.UserDetailsImpl;
import fontys.s3.backend.domain.LoginRequest;
import fontys.s3.backend.domain.LoginResponse;
import fontys.s3.backend.persistence.UserRepository;
import fontys.s3.backend.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/*@RunWith(SpringRunner.class)
@SpringBootTest*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
class LoginUseCaseImplTest {
    @Mock
    private UserRepository userRepository;

/*    @MockBean
    private Authentication authentication;*/

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailsImpl userDetails;

    @Spy
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Spy
    private JwtUtils jwtUtils = new JwtUtils("E91E158E4C6656F68B1B5D1C316766DE98D2AD6EF3BFB44F78E9CFCDF5");
    @InjectMocks
    private LoginUseCaseImpl useCase;


    @Test
    @WithMockUser(username = "john@gmail.com")
    void login() {
        //arrange
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserEntity currentUser = userRepository.findByEmail(auth.getName()).orElse(null);

/*        UserRoleEntity userRoleEntity = UserRoleEntity.builder()
                .id(1L)
                .role(RoleEnum.USER)
                .user(null)
                .build();

        UserEntity user = UserEntity.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@gmail.com")
                .password(passwordEncoder.encode("password"))
                .userRoles(Set.of(userRoleEntity))
                .build();
        when(userRepository.findByEmail("john@gmail.com"))
                .thenReturn(Optional.of(user));*/

/*        userDetails = UserDetailsImpl.build(user);
        when(authentication.getPrincipal()).thenReturn(userDetails);*/

        LoginRequest request = LoginRequest.builder()
                .email("john@gmail.com")
                .password("password")
                .build();

        UsernamePasswordAuthenticationToken principal = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        //authentication = authenticationManager.authenticate(principal);
        when(authenticationManager.authenticate(any())).thenReturn(auth);
        //when(authentication.getPrincipal()).thenReturn(principal);

        //act
        LoginResponse actual = useCase.login(request);
        LoginResponse expected = LoginResponse.builder()
                .accessToken("accessToken")
                .email(auth.getName())
                .roles(List.of("USER"))
                .userId(auth.getPrincipal().hashCode())
                .build();

        //assert
        assertEquals(expected.getUserId(), actual.getUserId());
        assertEquals(expected.getEmail(), actual.getEmail());

        verify(userRepository).findByEmail("john@gmail.com");
    }
}