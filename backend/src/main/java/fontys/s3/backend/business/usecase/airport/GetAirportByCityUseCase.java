package fontys.s3.backend.business.usecase.airport;

import fontys.s3.backend.domain.request.GetAirportByCityRequest;
import fontys.s3.backend.domain.response.GetAirportResponse;

public interface GetAirportByCityUseCase {
    GetAirportResponse getAirportByCity(GetAirportByCityRequest request);
}
