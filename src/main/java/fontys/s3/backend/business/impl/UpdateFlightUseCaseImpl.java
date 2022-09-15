package fontys.s3.backend.business.impl;

import fontys.s3.backend.business.UpdateFlightUseCase;
import fontys.s3.backend.business.exception.InvalidFlightException;
import fontys.s3.backend.domain.UpdateFlightRequest;
import fontys.s3.backend.persistence.AirportRepository;
import fontys.s3.backend.persistence.FlightRepository;
import fontys.s3.backend.persistence.entity.FlightEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateFlightUseCaseImpl implements UpdateFlightUseCase {
    private final FlightRepository flightRepository;
    private final AirportRepository airportRepository;

    @Override
    public void updateFlight(UpdateFlightRequest request) {
        Optional<FlightEntity> flightOptional = flightRepository.findById(request.getFlightNumber());
        if (flightOptional.isEmpty()) {
            throw new InvalidFlightException("FLIGHT_NUMBER_INVALID");
        }

        FlightEntity flight = flightOptional.get();
        updateFields(request, flight);
    }

    private void updateFields(UpdateFlightRequest request, FlightEntity flight) {
        flight.setFlightTime(request.getFlightTime());
        flight.setLandTime(request.getLandTime());
        flight.setEconomicPrice(request.getEconomicPrice());
        flight.setBusinessPrice(request.getBusinessPrice());

        flightRepository.save(flight);
    }
}
