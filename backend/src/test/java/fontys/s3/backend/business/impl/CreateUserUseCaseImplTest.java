package fontys.s3.backend.business.impl;

import fontys.s3.backend.business.exception.InvalidCredentialsException;
import fontys.s3.backend.business.impl.user.CreateUserUseCaseImpl;
import fontys.s3.backend.domain.request.CreateUserRequest;
import fontys.s3.backend.domain.response.CreateUserResponse;
import fontys.s3.backend.persistence.UserRepository;
import fontys.s3.backend.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private CreateUserUseCaseImpl createUserUseCase;

    @Test
    void createUserWhenEmailIsAlreadyTakenThenThrowException() {
        CreateUserRequest request = CreateUserRequest.builder().email("test@test.com").build();

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        InvalidCredentialsException exception =
                assertThrows(
                        InvalidCredentialsException.class, () -> createUserUseCase.createUser(request, null));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void createUserWhenEmailIsNotTaken() {
        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .firstName("firstName")
                .lastName("lastName")
                .email("email@email.com")
                .password("password")
                .build();

        CreateUserRequest request =
                CreateUserRequest.builder()
                        .firstName("firstName")
                        .lastName("lastName")
                        .email("email@email.com")
                        .password("password")
                        .build();

        MimeMessage mimeMessage = mock(MimeMessage.class);

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(userEntity);
        when(httpServletRequest.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080"));
        when(httpServletRequest.getServletPath()).thenReturn("/api/v1/user");
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        doNothing().when(mailSender).send(mimeMessage);

        CreateUserResponse response = createUserUseCase.createUser(request, httpServletRequest);

        assertNotNull(response);
        verify(userRepository, times(1)).save(any());
    }

}
