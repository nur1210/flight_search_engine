package fontys.s3.backend.business.impl;

import fontys.s3.backend.business.GetAirportUseCase;
import fontys.s3.backend.domain.Airport;
import fontys.s3.backend.persistence.AirportRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@AllArgsConstructor
public class GetCountyUseCaseImpl implements GetAirportUseCase {

    private AirportRepository airportRepository;

    @Override
    public Optional<Airport> getAirport(String airportCode) {
        return airportRepository.findById(airportCode).map(AirportConverter::convert);
    }
}
