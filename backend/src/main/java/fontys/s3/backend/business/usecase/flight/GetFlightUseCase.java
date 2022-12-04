package fontys.s3.backend.business.usecase.flight;

import fontys.s3.backend.domain.model.Flight;

import java.util.Optional;

public interface GetFlightUseCase {
    Optional<Flight> getFlight(long flightId);
}
