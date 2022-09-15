package fontys.s3.backend.business.impl;

import fontys.s3.backend.business.GetFlightsUseCase;
import fontys.s3.backend.domain.Flight;
import fontys.s3.backend.domain.GetAllFlightsRequest;
import fontys.s3.backend.domain.GetAllFlightsResponse;
import fontys.s3.backend.persistence.FlightRepository;
import fontys.s3.backend.persistence.entity.FlightEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@AllArgsConstructor
public class GetFlightsUseCaseImpl implements GetFlightsUseCase {
    private FlightRepository flightRepository;
    @Override
    public GetAllFlightsResponse getFlights(final GetAllFlightsRequest request) {
        List<FlightEntity> results;
        if (StringUtils.hasText(request.getOrigin()) && StringUtils.hasText(request.getDestination())) {
            results = flightRepository.FindAllFlightsFromOriginCodeToDestinationCode(request.getOrigin(), request.getDestination());
        } else if (StringUtils.hasText(request.getOrigin())) {
            results = flightRepository.FindTop3CheapestFlightFromOrigin(request.getOrigin());
        }
        else {
            results = flightRepository.findAll();
        }

        final GetAllFlightsResponse response = new GetAllFlightsResponse();
        List<Flight> flights = results
                .stream()
                .map(FlightConverter::convert)
                .toList();
        response.setFlights(flights);

        return response;
    }
}
