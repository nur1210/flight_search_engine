package fontys.s3.backend.domain.request;

import fontys.s3.backend.persistence.entity.RouteEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateFlightRequest {
    private List<RouteEntity> route;
    private double price;
    private long availableSeats;
}
