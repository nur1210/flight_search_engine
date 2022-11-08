package fontys.s3.backend.business;

import fontys.s3.backend.domain.CreateFlightRequest;
import fontys.s3.backend.domain.CreateFlightResponse;

public interface    CreateFlightUseCase {
    CreateFlightResponse createFlight(CreateFlightRequest request);
}
