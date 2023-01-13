package fontys.s3.backend.business.impl.user;

import fontys.s3.backend.business.exception.InvalidUserException;
import fontys.s3.backend.domain.request.UpdateUserRequest;
import fontys.s3.backend.persistence.UserRepository;
import fontys.s3.backend.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Profile("test")
class UpdateUserUseCaseImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UpdateUserUseCaseImpl updateUserUseCase;

    @Test
    void updateUser_ShouldUpdateUserDetails() {
        // Arrange
        var userEntity = UserEntity.builder().build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.matches("currentPassword", userEntity.getPassword())).thenReturn(true);
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedPassword");

        // Act
        updateUserUseCase.updateUser(1L, UpdateUserRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@example.com")
                .currentPassword("currentPassword")
                .newPassword("newPassword")
                .build());

        // Assert
        verify(userRepository).save(userEntity);
        assertEquals("John", userEntity.getFirstName());
        assertEquals("Doe", userEntity.getLastName());
        assertEquals("johndoe@example.com", userEntity.getEmail());
        assertEquals("encodedPassword", userEntity.getPassword());
    }

    @Test
    void updateUser_ShouldThrowInvalidUserException_WhenUserNotFound() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        assertThrows(InvalidUserException.class, () -> updateUserUseCase.updateUser(1L, UpdateUserRequest.builder().build()));
    }

    @Test
    void updateUser_ShouldNotUpdatePassword_WhenNewPasswordIsNull() {
        // Arrange
        var userEntity = UserEntity.builder().password("oldPassword").build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));

        // Act
        updateUserUseCase.updateUser(1L, UpdateUserRequest.builder().build());

        // Assert
        verify(userRepository).save(userEntity);
        assertEquals("oldPassword", userEntity.getPassword());
    }

    @Test
    void updateUser_ShouldNotUpdatePassword_WhenNewPasswordIsEmpty() {
        // Arrange
        var userEntity = UserEntity.builder().password("oldPassword").build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));

        // Act
        updateUserUseCase.updateUser(1L, UpdateUserRequest.builder().newPassword("").build());

        // Assert
        verify(userRepository, atMostOnce()).save(userEntity);
        assertEquals("oldPassword", userEntity.getPassword());
    }

}