package fontys.s3.backend.business;

import fontys.s3.backend.domain.UpdateFlightRequest;

public interface UpdateFlightUseCase {
    void updateFlight(UpdateFlightRequest request);
}
