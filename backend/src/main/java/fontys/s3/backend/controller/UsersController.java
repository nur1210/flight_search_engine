package fontys.s3.backend.controller;

import fontys.s3.backend.business.exception.UnauthorizedDataAccessException;
import fontys.s3.backend.business.usecase.user.*;
import fontys.s3.backend.configuration.security.isauthenticated.IsAuthenticated;
import fontys.s3.backend.domain.model.User;
import fontys.s3.backend.domain.request.CreateUserRequest;
import fontys.s3.backend.domain.request.UpdateUserRequest;
import fontys.s3.backend.domain.response.CreateUserResponse;
import fontys.s3.backend.domain.response.GetAllUsersResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequiredArgsConstructor
public class UsersController {
    private final GetUserUseCase getUserUseCase;
    private final GetAllUsersUseCase getAllUsersUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;

    @IsAuthenticated
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("{id}")
    public ResponseEntity<User> getUser(@PathVariable(value = "id") final long id) {
        final Optional<User> userOptional = getUserUseCase.getUser(id);
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(userOptional.get());
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN"})
    @GetMapping()
    public ResponseEntity<GetAllUsersResponse> getAllUsers() {
        try {
            GetAllUsersResponse response = GetAllUsersResponse.builder()
                    .users(getAllUsersUseCase.getAllUsers())
                    .build();
            return ResponseEntity.ok(response);
        } catch (UnauthorizedDataAccessException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN"})
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable(value = "id") long userId) {
        deleteUserUseCase.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping()
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody @Valid CreateUserRequest request) {
        CreateUserResponse response = createUserUseCase.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody @Valid UpdateUserRequest request) {
        updateUserUseCase.updateUser(id, request);
        return ResponseEntity.noContent().build();
    }
}
