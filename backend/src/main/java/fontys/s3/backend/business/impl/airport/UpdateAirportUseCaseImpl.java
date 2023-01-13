package fontys.s3.backend.business.impl.airport;

import fontys.s3.backend.business.usecase.airport.UpdateAirportUseCase;
import fontys.s3.backend.domain.request.UpdateAirportRequest;
import fontys.s3.backend.persistence.AirportRepository;
import fontys.s3.backend.persistence.entity.AirportEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateAirportUseCaseImpl implements UpdateAirportUseCase {
    private AirportRepository airportRepository;

    @Override
    public void updateAirport(UpdateAirportRequest request) {
        AirportEntity airport = airportRepository.findById(request.getIata()).orElseThrow(() -> new RuntimeException("AIRPORT_NOT_FOUND"));
        updateFields(request, airport);
    }

    private void updateFields(UpdateAirportRequest request, AirportEntity airport) {
        airport.setIata(request.getIata());
        airportRepository.save(airport);
    }
}

