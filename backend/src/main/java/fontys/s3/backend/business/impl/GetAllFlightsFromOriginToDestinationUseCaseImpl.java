package fontys.s3.backend.business.impl;

import fontys.s3.backend.business.GetAllFlightsFromOriginToDestinationUseCase;
import fontys.s3.backend.business.impl.converter.FlightConverter;
import fontys.s3.backend.domain.Flight;
import fontys.s3.backend.domain.FlightParams;
import fontys.s3.backend.domain.GetAllFlightsFromOriginToDestinationRequest;
import fontys.s3.backend.domain.GetAllFlightsFromOriginToDestinationResponse;
import fontys.s3.backend.persistence.entity.FlightEntity;
import fontys.s3.backend.persistence.tequilaapi.TequilaFlightsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetAllFlightsFromOriginToDestinationUseCaseImpl implements GetAllFlightsFromOriginToDestinationUseCase {
    private TequilaFlightsRepository flightInfoRepository;

    @Override
    public GetAllFlightsFromOriginToDestinationResponse getAllFlightsFromOriginToDestination(final GetAllFlightsFromOriginToDestinationRequest request) {

        List<FlightEntity> results;

        FlightParams flightParams = FlightParams.builder()
                .flyFrom(request.getFlyFrom())
                .flyTo(request.getFlyTo())
                .dateFrom(request.getDateFrom())
                .dateTo(request.getDateTo())
                .returnFrom(request.getReturnFrom())
                .returnTo(request.getReturnTo())
                .flightType(request.getFlightType())
                .adults(String.valueOf(request.getAdults()))
                .selectedCabins(request.getSelectedCabins())
                .currency(request.getCurrency())
                .language(request.getLanguage())
                .maxStopovers(String.valueOf(request.getMaxStopovers()))
                .maxSectorStopovers(String.valueOf(request.getMaxSectorStopovers()))
                .build();

            results = flightInfoRepository.getFlightsInfo(flightParams);


        final GetAllFlightsFromOriginToDestinationResponse response = new GetAllFlightsFromOriginToDestinationResponse();
        List<Flight> flights = results
                .stream()
                .map(FlightConverter::convert)
                .toList();
        response.setFlights(flights);

        return response;
    }
}
