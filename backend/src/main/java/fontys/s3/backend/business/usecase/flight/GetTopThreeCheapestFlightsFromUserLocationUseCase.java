package fontys.s3.backend.business.usecase.flight;

import fontys.s3.backend.domain.request.GetTopThreeCheapestFlightsFromUserLocationRequest;
import fontys.s3.backend.domain.response.GetFlightsResponse;

public interface GetTopThreeCheapestFlightsFromUserLocationUseCase {
    GetFlightsResponse getTopThreeCheapestFlightsFromUserLocation(GetTopThreeCheapestFlightsFromUserLocationRequest request);
}
