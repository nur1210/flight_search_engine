package fontys.s3.backend.business.impl.airport;

import fontys.s3.backend.business.impl.converter.AirportConverter;
import fontys.s3.backend.business.usecase.airport.GetAirportByCityUseCase;
import fontys.s3.backend.domain.request.GetAirportByCityRequest;
import fontys.s3.backend.domain.response.GetAirportResponse;
import fontys.s3.backend.persistence.tequilaapi.TequilaAirportsRepository;
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
