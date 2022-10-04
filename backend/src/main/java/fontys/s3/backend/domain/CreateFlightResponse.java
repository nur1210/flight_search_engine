package fontys.s3.backend.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateFlightResponse {
    private long flightId;
}
