package fontys.s3.backend.business.impl;

/*
@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CreateUserUseCaseImpl createUserUseCase;

    @Test
    @DisplayName("Should throw an exception when the email is already taken")
    void createUserWhenEmailIsAlreadyTakenThenThrowException() {
        CreateUserRequest request = CreateUserRequest.builder().email("test@test.com").build();

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        ResponseStatusException exception =
                assertThrows(
                        ResponseStatusException.class, () -> createUserUseCase.createUser(request));

        assertEquals("400 BAD_REQUEST \"Email already taken\"", exception.getMessage());
    }

    @Test
    @DisplayName("Should save the user when the email is not taken")
    void createUserWhenEmailIsNotTaken() {
        CreateUserRequest request =
                CreateUserRequest.builder()
                        .firstName("firstName")
                        .lastName("lastName")
                        .email("email@email.com")
                        .password("password")
                        .build();

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);

        CreateUserResponse response = createUserUseCase.createUser(request);

        assertNotNull(response);
        assertEquals(1, response.getUserId());

        verify(userRepository, times(1)).existsByEmail(request.getEmail());
        verify(userRepository, times(1)).save(any());
    }
}*/
