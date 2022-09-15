package fontys.s3.backend.business.impl;

import fontys.s3.backend.business.GetAirportsUseCase;
import fontys.s3.backend.domain.Airport;
import fontys.s3.backend.domain.GetAllAirportsResponse;
import fontys.s3.backend.persistence.AirportRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetCountriesUseCaseImpl implements GetAirportsUseCase {
    private final AirportRepository airportRepository;

/*    @Override
    public GetAllCountriesResponse getCountries() {
        List<Country> countries = countryRepository.findAll()
                .stream()
                .map(CountryConverter::convert)
                .toList();

        return GetCountriesResponse.builder()
                .countries(countries)
                .build();
    }*/

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
