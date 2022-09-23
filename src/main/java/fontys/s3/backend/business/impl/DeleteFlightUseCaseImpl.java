package fontys.s3.backend.business.impl;

import fontys.s3.backend.business.DeleteFlightUseCase;
import fontys.s3.backend.persistence.FlightRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteFlightUseCaseImpl implements DeleteFlightUseCase {
    private final FlightRepository flightRepository;

    @Override
    public void deleteFlight(long flightId) {
        this.flightRepository.deleteById(flightId);
    }
}
