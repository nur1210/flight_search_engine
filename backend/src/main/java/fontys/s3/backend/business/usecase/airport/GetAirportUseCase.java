package fontys.s3.backend.business.usecase.airport;

import fontys.s3.backend.domain.model.Airport;

import java.util.Optional;

public interface GetAirportUseCase {
    Optional<Airport> getAirport(String iataCode);
}
