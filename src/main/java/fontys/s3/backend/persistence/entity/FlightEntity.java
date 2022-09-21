package fontys.s3.backend.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;


//@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "flight")
@Table(name = "flights")
public class FlightEntity {
    @Id
    @Column(name = "flight_number")
    private String flightNumber;

    @Column(
            name = "airline",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String airline;

@JoinColumn(referencedColumnName = "iata")
@ManyToOne(cascade = CascadeType.ALL, targetEntity = AirportEntity.class)
    private AirportEntity departureAirport;

    @JoinColumn(referencedColumnName = "iata")
    @ManyToOne(cascade = CascadeType.ALL, targetEntity = AirportEntity.class)
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

    @Column(
            name = "price",
            columnDefinition = "double precision"
    )
    private double price;


    @Column(
            name = "available_seats",
            columnDefinition = "integer"
    )
    private long availableSeats;

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(long availableSeats) {
        this.availableSeats = availableSeats;
    }
}
