package fontys.s3.backend.business.impl;

import fontys.s3.backend.business.GetAirportByCordsUseCase;
import fontys.s3.backend.domain.GetAirportByCordsRequest;
import fontys.s3.backend.domain.GetAirportResponse;
import fontys.s3.backend.persistence.tequilaApi.TequilaAirportsRepository;
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
