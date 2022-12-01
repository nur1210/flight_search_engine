package fontys.s3.backend.controller;

import fontys.s3.backend.business.GetUserUseCase;
import fontys.s3.backend.business.exception.UnauthorizedDataAccessException;
import fontys.s3.backend.business.impl.GetAllUsersUseCaseImpl;
import fontys.s3.backend.domain.AccessToken;
import fontys.s3.backend.domain.GetAllUsersResponse;
import fontys.s3.backend.domain.User;
import fontys.s3.backend.persistence.entity.RoleEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UsersControllerTest {
    @MockBean
    private AccessToken accessToken;
    @Mock
    private GetUserUseCase getUserUseCase;
    @Mock
    private GetAllUsersUseCaseImpl getAllUsersUseCase;
    @InjectMocks
    private UsersController usersController;

    @Test
    @WithMockUser(username = "test")
    void getAllUsersWhenUserIsNotAuthorized() {
        when(accessToken.hasRole(anyString())).thenReturn(false);
        when(getAllUsersUseCase.getAllUsers()).thenThrow(new UnauthorizedDataAccessException("USER_IS_NOT_ADMIN"));

        ResponseEntity<GetAllUsersResponse> response = usersController.getAllUsers();

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    @WithMockUser(username = "test", roles = "{ADMIN}")
    void getAllUsersWhenUserIsAuthorized() {
        //TODO help
        List<User> users = mock(List.class);
        when(accessToken.hasRole(RoleEnum.ADMIN.name())).thenReturn(true);
        when(getAllUsersUseCase.getAllUsers()).thenReturn(users);

        ResponseEntity<GetAllUsersResponse> response = usersController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(getAllUsersUseCase, times(1)).getAllUsers();
    }

    @Test
    void getUserWhenUserIsNotFoundThenReturn404() {
        when(getUserUseCase.getUser(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<User> response = usersController.getUser(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getUserWhenUserIsFoundThenReturn200() {
        User user = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@gmail.com")
                .password("password")
                .build();

        when(getUserUseCase.getUser(anyLong())).thenReturn(Optional.of(user));

        ResponseEntity<User> response = usersController.getUser(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }
}