package fontys.s3.backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Flight {
    private long id;
    private String flyFrom;
    private String flyTo;
    private List<Route> outboundRoutes;
    private List<Route> returnRoutes;
    private double price;
    private long availableSeats;
    private String deepLink;
}
