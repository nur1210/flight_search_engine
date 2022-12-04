package fontys.s3.backend.business.usecase.airport;

import fontys.s3.backend.domain.request.GetAirportByCordsRequest;
import fontys.s3.backend.domain.response.GetAirportResponse;

public interface GetAirportByCordsUseCase {
    GetAirportResponse getAirportByCords(GetAirportByCordsRequest request);
}
