package fontys.s3.backend.business;

import fontys.s3.backend.domain.GetAllFlightsFromOriginToDestinationRequest;
import fontys.s3.backend.domain.GetAllFlightsFromOriginToDestinationResponse;

public interface GetAllFlightsFromOriginToDestinationUseCase {
    GetAllFlightsFromOriginToDestinationResponse getFlights(GetAllFlightsFromOriginToDestinationRequest request);
}
