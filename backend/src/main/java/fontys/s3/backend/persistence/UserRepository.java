package fontys.s3.backend.persistence;

import fontys.s3.backend.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    Optional<UserEntity> findByEmail(String email);
    Boolean existsByEmail(String email);
}
