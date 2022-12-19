package fontys.s3.backend.business.impl.flight;

import fontys.s3.backend.business.usecase.flight.CreateFlightUseCase;
import fontys.s3.backend.domain.request.CreateFlightRequest;
import fontys.s3.backend.domain.response.CreateFlightResponse;
import fontys.s3.backend.persistence.FlightRepository;
import fontys.s3.backend.persistence.entity.FlightEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateFlightUseCaseImpl implements CreateFlightUseCase {
    private final FlightRepository flightRepository;

    @Override
    public CreateFlightResponse createFlight(CreateFlightRequest request) {

        FlightEntity saveFlight = saveNewFlight(request);

        return CreateFlightResponse.builder()
                .flightId(saveFlight.getId())
                .build();
    }

    private FlightEntity saveNewFlight(CreateFlightRequest request) {
        FlightEntity newFlight = FlightEntity.builder()
                .outboundRoutes(request.getOutboundRoutes())
                .returnRoutes(request.getReturnRoutes())
                .price(request.getPrice())
                .availableSeats(request.getAvailableSeats())
                .build();


        return flightRepository.save(newFlight);
    }
}
