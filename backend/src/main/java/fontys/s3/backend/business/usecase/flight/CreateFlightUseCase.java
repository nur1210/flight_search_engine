package fontys.s3.backend.business.usecase.flight;

import fontys.s3.backend.domain.request.CreateFlightRequest;
import fontys.s3.backend.domain.response.CreateFlightResponse;

public interface CreateFlightUseCase {
    CreateFlightResponse createFlight(CreateFlightRequest request);
}
