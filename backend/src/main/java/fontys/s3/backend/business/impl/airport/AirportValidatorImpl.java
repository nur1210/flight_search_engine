package fontys.s3.backend.business.impl.airport;

import fontys.s3.backend.business.exception.InvalidAirportException;
import fontys.s3.backend.business.usecase.airport.AirportValidator;
import fontys.s3.backend.persistence.AirportRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AirportValidatorImpl implements AirportValidator {
    private final AirportRepository airportRepository;

    @Override
    public void validAirport(String airportCode){
        if (airportCode == null || !airportRepository.existsById(airportCode)) {
            throw new InvalidAirportException();
        }
    }
}
