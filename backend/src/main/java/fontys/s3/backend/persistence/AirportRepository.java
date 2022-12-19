package fontys.s3.backend.persistence;

import fontys.s3.backend.persistence.entity.AirportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirportRepository extends JpaRepository<AirportEntity, String> {
    @Query("SELECT a FROM Airport a WHERE a.city = ?1")
    Optional<AirportEntity> findByCity(String city);
}
