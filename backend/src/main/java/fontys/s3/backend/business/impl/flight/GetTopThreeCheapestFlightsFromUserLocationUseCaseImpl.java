package fontys.s3.backend.business.impl.flight;

import fontys.s3.backend.business.impl.converter.FlightConverter;
import fontys.s3.backend.business.usecase.flight.GetTopThreeCheapestFlightsFromUserLocationUseCase;
import fontys.s3.backend.domain.model.Flight;
import fontys.s3.backend.domain.model.FlightParams;
import fontys.s3.backend.domain.request.GetTopThreeCheapestFlightsFromUserLocationRequest;
import fontys.s3.backend.domain.response.GetTopThreeCheapestFlightsFromUserLocationResponse;
import fontys.s3.backend.persistence.entity.FlightEntity;
import fontys.s3.backend.persistence.tequilaapi.TequilaFlightsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class GetTopThreeCheapestFlightsFromUserLocationUseCaseImpl implements GetTopThreeCheapestFlightsFromUserLocationUseCase {
    private TequilaFlightsRepository flightInfoRepository;

    @Override
    public GetTopThreeCheapestFlightsFromUserLocationResponse getTopThreeCheapestFlightsFromUserLocation(GetTopThreeCheapestFlightsFromUserLocationRequest request) {
        List<FlightEntity> results;

        FlightParams flightParams = FlightParams.builder()
                .flyFrom(request.getFlyFrom())
                .dateFrom(request.getDateFrom())
                .dateTo(request.getDateTo())
                .returnFrom(request.getReturnFrom())
                .returnTo(request.getReturnTo())
                .minNightsInDestination(String.valueOf(request.getMinNightsInDestination()))
                .maxNightsInDestination(String.valueOf(request.getMaxNightsInDestination()))
                .returnFromDifferentCity(String.valueOf(request.isReturnFromDifferentCity()))
                .returnToDifferentCity(String.valueOf(request.isReturnToDifferentCity()))
                .resultsPerDestination(String.valueOf(request.getResultsPerDestination()))
                .onlyWorkingDays(String.valueOf(request.isOnlyWorkingDays()))
                .onlyWeekends(String.valueOf(request.isOnlyWeekends()))
                .limit(String.valueOf(request.getLimit()))
                .flightType(request.getFlightType())
                .adults(String.valueOf(request.getAdults()))
                .selectedCabins(request.getSelectedCabins())
                .currency(request.getCurrency())
                .language(request.getLanguage())
                .maxStopovers(String.valueOf(request.getMaxStopovers()))
                .maxSectorStopovers(String.valueOf(request.getMaxSectorStopovers()))
                .build();


        results = flightInfoRepository.getFlightsInfo(flightParams);


        final GetTopThreeCheapestFlightsFromUserLocationResponse response = new GetTopThreeCheapestFlightsFromUserLocationResponse();
        List<Flight> flights = results
                .stream()
                .map(FlightConverter::convert)
                .toList();
        response.setCheapestFlights(flights);

        return response;
    }
}
