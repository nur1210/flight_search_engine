package fontys.s3.backend.business.impl.flight;

import fontys.s3.backend.business.impl.converter.FlightConverter;
import fontys.s3.backend.business.usecase.flight.GetFlightUseCase;
import fontys.s3.backend.domain.model.Flight;
import fontys.s3.backend.persistence.FlightRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetFlightUseCaseImpl implements GetFlightUseCase {

    private FlightRepository flightRepository;

    @Override
    public Optional<Flight> getFlight(long flightId) {
        return flightRepository.findById(flightId).map(FlightConverter::convert);
    }
}
