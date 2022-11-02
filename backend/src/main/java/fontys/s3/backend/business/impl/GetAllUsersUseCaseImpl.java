package fontys.s3.backend.business.impl;

import fontys.s3.backend.business.GetAllUsersUseCase;
import fontys.s3.backend.business.impl.converter.UserConverter;
import fontys.s3.backend.domain.User;
import fontys.s3.backend.persistence.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class GetAllUsersUseCaseImpl implements GetAllUsersUseCase {
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserConverter::convert)
                .toList();
    }
}
