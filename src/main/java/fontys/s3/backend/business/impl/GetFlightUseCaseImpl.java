package fontys.s3.backend.business.impl;

import fontys.s3.backend.business.GetFlightUseCase;
import fontys.s3.backend.domain.Flight;
import fontys.s3.backend.persistence.FlightRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetFlightUseCaseImpl implements GetFlightUseCase {

    private FlightRepository flightRepository;

    @Override
    public Optional<Flight> getFlight(String flightNumber) {
        return flightRepository.findById(flightNumber).map(FlightConverter::convert);
    }
}
