package fontys.s3.backend.persistence;

import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends FlightRepositoryBasic, FlightRepositoryCustom {
}
