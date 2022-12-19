package fontys.s3.backend.business.usecase.flight;

import fontys.s3.backend.domain.request.GetAdvanceSearchFlightsRequest;
import fontys.s3.backend.domain.response.GetFlightsResponse;

public interface GetAdvanceSearchFlightsUseCase {
    GetFlightsResponse getAdvanceSearchFlights(GetAdvanceSearchFlightsRequest request);
}
