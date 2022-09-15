package fontys.s3.backend.persistence;

import fontys.s3.backend.persistence.entity.FlightEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepositoryBasic extends JpaRepository<FlightEntity, String>{
}
