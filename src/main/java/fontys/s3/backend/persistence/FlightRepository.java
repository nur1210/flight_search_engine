package fontys.s3.backend.persistence;

import fontys.s3.backend.persistence.entity.FlightEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends FlightRepositoryBasic, FlightRepositoryCustom {
    @Query(value = "SELECT * FROM flights f WHERE f.origin_code = ?1 ORDER BY f.economic_price LIMIT 3", nativeQuery = true)
    List<FlightEntity> FindTop3CheapestFlightFromOrigin(String originCode);
}
