package fontys.s3.backend.domain.response;

import fontys.s3.backend.domain.model.Airport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllAirportsResponse {
    private List<Airport> airports;
}
