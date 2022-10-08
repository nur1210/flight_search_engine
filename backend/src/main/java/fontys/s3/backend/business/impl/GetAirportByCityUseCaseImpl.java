package fontys.s3.backend.business.impl;

import fontys.s3.backend.business.GetAirportByCityUseCase;
import fontys.s3.backend.domain.GetAirportByCityRequest;
import fontys.s3.backend.domain.GetAirportResponse;
import fontys.s3.backend.persistence.tequilaApi.TequilaAirportsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetAirportByCityUseCaseImpl implements GetAirportByCityUseCase {

    private final TequilaAirportsRepository tequilaAirportsRepository;

    @Override
    public GetAirportResponse getAirportByCity(GetAirportByCityRequest request) {
        var result = tequilaAirportsRepository.getAirportByCity(request.getCity());
        return GetAirportResponse.builder()
                .airport(AirportConverter.convert(result))
                .build();
    }
}
