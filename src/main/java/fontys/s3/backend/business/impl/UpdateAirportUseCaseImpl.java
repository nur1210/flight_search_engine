package fontys.s3.backend.business.impl;

import fontys.s3.backend.business.UpdateAirportUseCase;
import fontys.s3.backend.domain.UpdateAirportRequest;
import fontys.s3.backend.persistence.AirportRepository;
import fontys.s3.backend.persistence.entity.AirportEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateAirportUseCaseImpl implements UpdateAirportUseCase {
    private AirportRepository airportRepository;

    @Override
    public void UpdateAirport(UpdateAirportRequest request) {
        AirportEntity airport = airportRepository.findById(request.getInitials()).orElseThrow(() -> new RuntimeException("COUNTRY_NOT_FOUND"));
        updateFields(request, airport);
    }

    private void updateFields(UpdateAirportRequest request, AirportEntity airport) {
        airport.setAirportCode(request.getInitials());
        airport.setName(request.getName());
        airportRepository.save(airport);
    }
}

