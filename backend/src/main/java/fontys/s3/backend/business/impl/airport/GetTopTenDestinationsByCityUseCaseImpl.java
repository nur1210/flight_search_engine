package fontys.s3.backend.business.impl.airport;

import fontys.s3.backend.business.usecase.airport.GetTopTenDestinationsByCityUseCase;
import fontys.s3.backend.domain.response.GetTopTenDestinationsResponse;
import fontys.s3.backend.persistence.tequilaapi.TequilaAirportsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetTopTenDestinationsByCityUseCaseImpl implements GetTopTenDestinationsByCityUseCase {
    private final TequilaAirportsRepository tequilaAirportsRepository;

    @Override
    public GetTopTenDestinationsResponse getTopTenDestinationsByCity(String city) {
        var result = tequilaAirportsRepository.getTopTenDestinationsFromOrigin(city);
        return GetTopTenDestinationsResponse.builder()
                .destinations(result)
                .build();
    }
}
