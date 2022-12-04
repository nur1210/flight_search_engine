package fontys.s3.backend.business.impl.airport;

import fontys.s3.backend.business.impl.converter.AirportConverter;
import fontys.s3.backend.business.usecase.airport.GetAirportsUseCase;
import fontys.s3.backend.domain.model.Airport;
import fontys.s3.backend.domain.response.GetAllAirportsResponse;
import fontys.s3.backend.persistence.AirportRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetAllAirportsUseCaseImpl implements GetAirportsUseCase {
    private final AirportRepository airportRepository;
    @Override
    public GetAllAirportsResponse getAllAirports() {
        final GetAllAirportsResponse response = new GetAllAirportsResponse();
        List<Airport> airports = airportRepository.findAll()
                .stream()
                .map(AirportConverter::convert)
                .toList();
        response.setAirports(airports);
        return response;
    }
}
