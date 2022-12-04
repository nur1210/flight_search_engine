package fontys.s3.backend.business.impl.airport;

import fontys.s3.backend.business.impl.converter.AirportConverter;
import fontys.s3.backend.business.usecase.airport.GetAirportByCordsUseCase;
import fontys.s3.backend.domain.request.GetAirportByCordsRequest;
import fontys.s3.backend.domain.response.GetAirportResponse;
import fontys.s3.backend.persistence.tequilaapi.TequilaAirportsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetAirportByCordsUseCaseImpl implements GetAirportByCordsUseCase {

    private final TequilaAirportsRepository tequilaAirportsRepository;

    @Override
    public GetAirportResponse getAirportByCords(GetAirportByCordsRequest request) {
       var result = tequilaAirportsRepository.getAirportByCords(request.getLatitude(), request.getLongitude());
         return GetAirportResponse.builder()
                .airport(AirportConverter.convert(result))
                .build();
    }
}
