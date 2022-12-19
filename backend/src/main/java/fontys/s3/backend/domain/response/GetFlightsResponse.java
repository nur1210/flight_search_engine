package fontys.s3.backend.domain.response;

import fontys.s3.backend.domain.model.Flight;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetFlightsResponse {
    private List<Flight> flights;
}
