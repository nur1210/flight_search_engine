package fontys.s3.backend.business.usecase.flight;

import fontys.s3.backend.domain.request.GetAllFlightsFromOriginToDestinationRequest;
import fontys.s3.backend.domain.response.GetFlightsResponse;

public interface GetAllFlightsFromOriginToDestinationUseCase {
    GetFlightsResponse getAllFlightsFromOriginToDestination(GetAllFlightsFromOriginToDestinationRequest request);
}
