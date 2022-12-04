package fontys.s3.backend.business.usecase.airport;

import fontys.s3.backend.domain.request.CreateAirportRequest;
import fontys.s3.backend.domain.response.CreateAirportResponse;

public interface CreateAirportUseCase {
    CreateAirportResponse createAirport(CreateAirportRequest request);
}
