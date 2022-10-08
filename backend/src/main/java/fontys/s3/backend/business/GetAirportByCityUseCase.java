package fontys.s3.backend.business;

import fontys.s3.backend.domain.GetAirportByCityRequest;
import fontys.s3.backend.domain.GetAirportResponse;

public interface GetAirportByCityUseCase {
    GetAirportResponse getAirportByCity(GetAirportByCityRequest request);
}
