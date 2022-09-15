package fontys.s3.backend.business;

import fontys.s3.backend.domain.Flight;

import java.util.Optional;

public interface GetFlightUseCase {
    Optional<Flight> getFlight(String flightNumber);
}
