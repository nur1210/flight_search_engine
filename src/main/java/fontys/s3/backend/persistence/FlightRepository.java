package fontys.s3.backend.persistence;

import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends FlightRepositoryBasic, FlightRepositoryCustom {
/*    @Query(value = "SELECT * FROM flights f WHERE f.departure_airport_iata = ?1 ORDER BY f.economic_price LIMIT 3", nativeQuery = true)
    List<FlightEntity> FindTop3CheapestFlightFromOrigin(String originCode);*/
}
