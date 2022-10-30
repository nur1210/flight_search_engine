package fontys.s3.backend.business.impl;

import fontys.s3.backend.business.GetAllFlightsFromOriginToDestinationUseCase;
import fontys.s3.backend.business.impl.converter.FlightConverter;
import fontys.s3.backend.domain.Flight;
import fontys.s3.backend.domain.GetAllFlightsFromOriginToDestinationRequest;
import fontys.s3.backend.domain.GetAllFlightsFromOriginToDestinationResponse;
import fontys.s3.backend.persistence.entity.FlightEntity;
import fontys.s3.backend.persistence.tequilaapi.TequilaFlightsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class GetAllFlightsFromOriginToDestinationUseCaseImpl implements GetAllFlightsFromOriginToDestinationUseCase {
    private TequilaFlightsRepository flightInfoRepository;

    @Override
    public GetAllFlightsFromOriginToDestinationResponse getAllFlightsFromOriginToDestination(final GetAllFlightsFromOriginToDestinationRequest request) {
        List<FlightEntity> results;
        Map<String, Object> params = new HashMap<>();
        //TODO move to own class
        ReflectionUtils.doWithFields(request.getClass(), field -> {
            params.put(field.getName(), field.get(request));
            field.setAccessible(true);
        });

        results = flightInfoRepository.getFlightsInfo(params);

        final GetAllFlightsFromOriginToDestinationResponse response = new GetAllFlightsFromOriginToDestinationResponse();
        List<Flight> flights = results
                .stream()
                .map(FlightConverter::convert)
                .toList();
        response.setFlights(flights);

        return response;
    }
}
