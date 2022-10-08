package fontys.s3.backend.business;

import fontys.s3.backend.domain.GetAirportByCordsRequest;
import fontys.s3.backend.domain.GetAirportResponse;

public interface GetAirportByCordsUseCase {
    GetAirportResponse getAirportByCords(GetAirportByCordsRequest request);
}
