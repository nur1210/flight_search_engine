package fontys.s3.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Flight {
    private String flightNumber;
    private String airline;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private Timestamp localDepartureTime;
    private Timestamp utcDepartureTime;
    private Timestamp localArrivalTime;
    private Timestamp utcArrivalTime;
    private double economicPrice;
    private double businessPrice;
}
