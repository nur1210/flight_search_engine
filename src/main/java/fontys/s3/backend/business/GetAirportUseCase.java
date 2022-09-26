package fontys.s3.backend.business;

import fontys.s3.backend.domain.Airport;

import java.util.Optional;

public interface GetAirportUseCase {
    Optional<Airport> getAirport(String iataCode);
}
