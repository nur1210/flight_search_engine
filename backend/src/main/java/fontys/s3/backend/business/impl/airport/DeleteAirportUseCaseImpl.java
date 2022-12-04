package fontys.s3.backend.business.impl.airport;

import fontys.s3.backend.business.usecase.airport.DeleteAirportUseCase;
import fontys.s3.backend.persistence.AirportRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteAirportUseCaseImpl implements DeleteAirportUseCase {

    private final AirportRepository airportRepository;
    @Override
    public void deleteAirport(String airportCode) {
        airportRepository.deleteById(airportCode);
    }
}
