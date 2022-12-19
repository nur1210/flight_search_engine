package fontys.s3.backend.business.impl.flight;

import fontys.s3.backend.business.impl.converter.FlightConverter;
import fontys.s3.backend.business.usecase.flight.GetAdvanceSearchFlightsUseCase;
import fontys.s3.backend.domain.model.Flight;
import fontys.s3.backend.domain.model.FlightParams;
import fontys.s3.backend.domain.request.GetAdvanceSearchFlightsRequest;
import fontys.s3.backend.domain.response.GetFlightsResponse;
import fontys.s3.backend.persistence.entity.FlightEntity;
import fontys.s3.backend.persistence.tequilaapi.TequilaFlightsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class GetAdvanceSearchFlightsUseCaseImpl implements GetAdvanceSearchFlightsUseCase {
    private TequilaFlightsRepository flightInfoRepository;

    @Override
    public GetFlightsResponse getAdvanceSearchFlights(GetAdvanceSearchFlightsRequest request) {
        List<FlightEntity> results;

        FlightParams flightParams = FlightParams.builder()
                .flyFrom(request.getFlyFrom())
                .flyTo(request.getFlyTo())
                .dateFrom(request.getDateFrom())
                .dateTo(request.getDateTo())
                .returnFrom(request.getReturnFrom())
                .returnTo(request.getReturnTo())
                .minNightsInDestination(String.valueOf(request.getMinNightsInDestination()))
                .maxNightsInDestination(String.valueOf(request.getMaxNightsInDestination()))
                .flightType(request.getFlightType())
                .onePerDestination(String.valueOf(request.getOnePerDestination()))
                .adults(String.valueOf(request.getAdults()))
                .selectedCabins(request.getSelectedCabins())
                .onlyWorkingDays(String.valueOf(request.isOnlyWorkingDays()))
                .onlyWeekends(String.valueOf(request.isOnlyWeekends()))
                .currency(request.getCurrency())
                .language(request.getLanguage())
                .maxStopovers(String.valueOf(request.getMaxStopovers()))
                .maxSectorStopovers(String.valueOf(request.getMaxSectorStopovers()))
                .limit(String.valueOf(request.getLimit()))
                .build();


        results = flightInfoRepository.getFlightsInfo(flightParams);


        final GetFlightsResponse response = new GetFlightsResponse();
        List<Flight> flights = results
                .stream()
                .map(FlightConverter::convert)
                .toList();
        response.setFlights(flights);

        return response;
    }
}
