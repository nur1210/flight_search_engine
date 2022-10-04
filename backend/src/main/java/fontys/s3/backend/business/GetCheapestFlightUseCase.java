package fontys.s3.backend.business;

import fontys.s3.backend.domain.GetCheapestFlightRequest;
import fontys.s3.backend.domain.GetCheapestFlightResponse;

public interface GetCheapestFlightUseCase {
    GetCheapestFlightResponse getCheapestFlight(GetCheapestFlightRequest request);
}
