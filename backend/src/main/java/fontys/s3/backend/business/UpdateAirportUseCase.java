package fontys.s3.backend.business;

import fontys.s3.backend.domain.UpdateAirportRequest;

public interface UpdateAirportUseCase {
    void updateAirport(UpdateAirportRequest request);
}
