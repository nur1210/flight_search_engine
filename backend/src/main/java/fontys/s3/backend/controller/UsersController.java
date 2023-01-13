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
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

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
    private final VerifyUserUseCase verifyUserUseCase;

    @IsAuthenticated
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("{id}")
    public ResponseEntity<User> getUser(@PathVariable(value = "id") final long id) {
        final Optional<User> userOptional = getUserUseCase.getUser(id);
        return userOptional.map(user -> ResponseEntity.ok().body(user))
                .orElseGet(() -> ResponseEntity.notFound().build());
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
    public CompletableFuture<ResponseEntity<CreateUserResponse>> createUser(@RequestBody @Valid CreateUserRequest request, HttpServletRequest httpServletRequest) {
        CompletableFuture<CreateUserResponse> responseFuture = createUserUseCase.createUser(request, httpServletRequest);
        return responseFuture.thenApply(response -> ResponseEntity.status(HttpStatus.CREATED).body(response));
    }

    @GetMapping("/verify/{code}")
    public ResponseEntity<HttpStatus> verifyUser(@PathVariable(value = "code") String code) {
        if (verifyUserUseCase.verifyUser(code)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody @Valid UpdateUserRequest request) {
        updateUserUseCase.updateUser(id, request);
        return ResponseEntity.noContent().build();
    }
}
