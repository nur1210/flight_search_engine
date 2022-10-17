package fontys.s3.backend.controller;

import fontys.s3.backend.business.CreateUserUseCase;
import fontys.s3.backend.domain.CreateUserRequest;
import fontys.s3.backend.domain.CreateUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {
    //private final GetUserUseCase getUserUseCase;
    //private final GetUsersUseCase getUsersUseCase;
    //private final DeleteUserUseCase deleteUserUseCase;
    private final CreateUserUseCase createUserUseCase;
    //private final UpdateUserUseCase updateUserUseCase;

/*    @GetMapping("{id}")
    public ResponseEntity<User> getUser(@PathVariable(value = "id") final long id) {
        final Optional<User> userOptional = getUserUseCase.getUser(id);
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(userOptional.get());
    }*/

/*    @GetMapping
    public ResponseEntity<GetAllUsersResponse> getAllUsers(@RequestParam(value = "country", required = false) String countryCode) {
        GetAllUsersRequest request = new GetAllUsersRequest();
        request.setCountryCode(countryCode);
        return ResponseEntity.ok(getUsersUseCase.getUsers(request));
    }*/

/*    @DeleteMapping("{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable int userId) {
        deleteUserUseCase.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }*/

    @PostMapping()
    public ResponseEntity<CreateUserResponse> createStudent(@RequestBody @Valid CreateUserRequest request) {
        CreateUserResponse response = createUserUseCase.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

/*    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody @Valid UpdateUserRequest request) {
        request.setId(id);
        updateUserUseCase.updateUser(request);
        return ResponseEntity.noContent().build();
    }*/
}
