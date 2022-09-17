package fontys.s3.backend.domain;

import fontys.s3.backend.persistence.entity.AirportEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateFlightRequest {
    private String flightNumber;
    private String airline;
    private AirportEntity departureAirport;
    private AirportEntity arrivalAirport;
    private Timestamp localDepartureTime;
    private Timestamp utcDepartureTime;
    private Timestamp localArrivalTime;
    private Timestamp utcArrivalTime;
    private double economicPrice;
    private double businessPrice;
}
