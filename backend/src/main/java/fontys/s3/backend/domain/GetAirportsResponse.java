package fontys.s3.backend.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetAirportsResponse {
    private List<Airport> airports;
}
