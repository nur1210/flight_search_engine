package fontys.s3.backend.business.usecase.flight;

import fontys.s3.backend.domain.request.GetCheapestFlightRequest;
import fontys.s3.backend.domain.response.GetCheapestFlightResponse;

public interface GetCheapestFlightUseCase {
    GetCheapestFlightResponse getCheapestFlight(GetCheapestFlightRequest request);
}
