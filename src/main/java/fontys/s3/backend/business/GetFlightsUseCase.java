package fontys.s3.backend.business;

import fontys.s3.backend.domain.GetAllFlightsRequest;
import fontys.s3.backend.domain.GetAllFlightsResponse;

public interface GetFlightsUseCase {
    GetAllFlightsResponse getFlights(GetAllFlightsRequest request);
}
