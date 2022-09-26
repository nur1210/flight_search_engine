package fontys.s3.backend.domain;

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
    //private long id;
    private List<RouteEntity> routes;
    private double price;
    private long availableSeats;
}
