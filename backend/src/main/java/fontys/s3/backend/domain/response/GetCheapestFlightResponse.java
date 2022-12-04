package fontys.s3.backend.domain.response;

import fontys.s3.backend.domain.model.Flight;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCheapestFlightResponse {
    private Flight flight;
}
