package fontys.s3.backend.business.usecase.airport;

import fontys.s3.backend.domain.response.GetTopTenDestinationsResponse;

public interface GetTopTenDestinationsByCityUseCase {
    GetTopTenDestinationsResponse getTopTenDestinationsByCity(String city);
}
