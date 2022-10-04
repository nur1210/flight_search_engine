package fontys.s3.backend.business;

import fontys.s3.backend.domain.CreateAirportRequest;
import fontys.s3.backend.domain.CreateAirportResponse;

public interface CreateAirportUseCase {
    CreateAirportResponse createAirport(CreateAirportRequest request);
}
