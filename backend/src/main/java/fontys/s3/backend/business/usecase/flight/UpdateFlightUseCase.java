package fontys.s3.backend.business.usecase.flight;

import fontys.s3.backend.domain.request.UpdateFlightRequest;

public interface UpdateFlightUseCase {
    void updateFlight(UpdateFlightRequest request);
}
