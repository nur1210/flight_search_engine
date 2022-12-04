package fontys.s3.backend.business.usecase.airport;

import fontys.s3.backend.domain.request.UpdateAirportRequest;

public interface UpdateAirportUseCase {
    void updateAirport(UpdateAirportRequest request);
}
