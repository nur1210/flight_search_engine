package fontys.s3.backend.business.impl.flight;

import fontys.s3.backend.business.usecase.flight.DeleteFlightUseCase;
import fontys.s3.backend.persistence.FlightRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Service
@AllArgsConstructor
public class DeleteFlightUseCaseImpl implements DeleteFlightUseCase {
    private final FlightRepository flightRepository;
    @Override
    public void deleteFlight(long flightId) {
        if (flightRepository.existsById(flightId)) {
            flightRepository.deleteById(flightId);
        } else {
            throw new NotFoundException("FLIGHT_NOT_FOUND");
        }
    }
}
