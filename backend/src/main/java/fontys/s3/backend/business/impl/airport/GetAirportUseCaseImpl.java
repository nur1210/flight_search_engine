package fontys.s3.backend.business.impl.airport;

import fontys.s3.backend.business.impl.converter.AirportConverter;
import fontys.s3.backend.business.usecase.airport.GetAirportUseCase;
import fontys.s3.backend.domain.model.Airport;
import fontys.s3.backend.persistence.AirportRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@AllArgsConstructor
public class GetAirportUseCaseImpl implements GetAirportUseCase {

    private AirportRepository airportRepository;

    @Override
    public Optional<Airport> getAirport(String iata) {
        return airportRepository.findById(iata).map(AirportConverter::convert);
    }
}
