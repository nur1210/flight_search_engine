package fontys.s3.backend.business.impl;

import fontys.s3.backend.business.CreateFlightUseCase;
import fontys.s3.backend.business.exception.FlightNumberAlreadyExistsException;
import fontys.s3.backend.domain.CreateFlightRequest;
import fontys.s3.backend.domain.CreateFlightResponse;
import fontys.s3.backend.persistence.FlightRepository;
import fontys.s3.backend.persistence.entity.FlightEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateFlightUseCaseImpl implements CreateFlightUseCase {
    private final FlightRepository flightRepository;

    @Override
    public CreateFlightResponse CreateFlight(CreateFlightRequest request) {
        if (flightRepository.existsById(request.getFlightNumber())) {
            throw new FlightNumberAlreadyExistsException();
        }

        FlightEntity saveFlight = saveNewFlight(request);

        return CreateFlightResponse.builder()
                .flightNumber(saveFlight.getFlightNumber())
                .build();
    }

    private FlightEntity saveNewFlight(CreateFlightRequest request) {
        FlightEntity newFlight = FlightEntity.builder()
                .flightNumber(request.getFlightNumber())
                .airline(request.getAirline())
                .departureAirport(request.getDepartureAirport())
                .arrivalAirport(request.getArrivalAirport())
                .localDepartureTime(request.getLocalDepartureTime())
                .utcDepartureTime(request.getUtcDepartureTime())
                .localArrivalTime(request.getLocalArrivalTime())
                .utcArrivalTime(request.getUtcArrivalTime())
                .economicPrice(request.getEconomicPrice())
                .businessPrice(request.getBusinessPrice())
                .build();

        return flightRepository.save(newFlight);
    }
}
