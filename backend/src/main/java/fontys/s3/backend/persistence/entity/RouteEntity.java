package fontys.s3.backend.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "route")
@Table(name = "routes")
public class RouteEntity {
    @Id
    @Column(name = "flight_number",
            columnDefinition = "integer")
    private long flightNumber;

    @Column(
            name = "airline",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String airline;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "departure_airport_iata")
    private AirportEntity departureAirport;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "arrival_airport_iata")
    private AirportEntity arrivalAirport;

    @Column(
            name = "local_departure_time",
            columnDefinition = "TIMESTAMP"
    )
    private Timestamp localDepartureTime;

    @Column(
            name = "utc_departure_time",
            columnDefinition = "TIMESTAMP"
    )
    private Timestamp utcDepartureTime;

    @Column(
            name = "local_arrival_time",
            columnDefinition = "TIMESTAMP"
    )
    private Timestamp localArrivalTime;

    @Column(
            name = "utc_arrival_time",
            columnDefinition = "TIMESTAMP"
    )
    private Timestamp utcArrivalTime;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private FlightEntity flight;

    public long getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(long flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public AirportEntity getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(AirportEntity departureAirport) {
        this.departureAirport = departureAirport;
    }

    public AirportEntity getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(AirportEntity arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public Timestamp getLocalDepartureTime() {
        return localDepartureTime;
    }

    public void setLocalDepartureTime(Timestamp localDepartureTime) {
        this.localDepartureTime = localDepartureTime;
    }

    public Timestamp getUtcDepartureTime() {
        return utcDepartureTime;
    }

    public void setUtcDepartureTime(Timestamp utcDepartureTime) {
        this.utcDepartureTime = utcDepartureTime;
    }

    public Timestamp getLocalArrivalTime() {
        return localArrivalTime;
    }

    public void setLocalArrivalTime(Timestamp localArrivalTime) {
        this.localArrivalTime = localArrivalTime;
    }

    public Timestamp getUtcArrivalTime() {
        return utcArrivalTime;
    }

    public void setUtcArrivalTime(Timestamp utcArrivalTime) {
        this.utcArrivalTime = utcArrivalTime;
    }

}
