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

/*    @Column(
            name = "departure_airport_iata",
            nullable = false,
            columnDefinition = "TEXT",
            length = 3
    )
    @JoinColumn(referencedColumnName = "iata")*/
@JoinColumn(referencedColumnName = "iata")
@ManyToOne(cascade = CascadeType.ALL, targetEntity = AirportEntity.class)
    private AirportEntity departureAirport;

/*    @Column(
            name = "arrival_airport_iata",
            nullable = false,
            columnDefinition = "TEXT",
            length = 3
    )*/
    @JoinColumn(referencedColumnName = "iata")
    @ManyToOne(cascade = CascadeType.ALL, targetEntity = AirportEntity.class)
    private AirportEntity arrivalAirport;

    @Column(
            name = "local_departure_time",
            columnDefinition = "TIMESTAMP",
            insertable = false,
            updatable = false
    )
    private Timestamp localDepartureTime;

    @Column(
            name = "utc_departure_time",
            columnDefinition = "TIMESTAMP",
            insertable = false,
            updatable = false
    )
    private Timestamp utcDepartureTime;

    @Column(
            name = "local_arrival_time",
            columnDefinition = "TIMESTAMP"
    )
    private Timestamp localArrivalTime;

    @Column(
            name = "utc_departure_time",
            columnDefinition = "TIMESTAMP"
    )
    private Timestamp utcArrivalTime;

    @Column(
            name = "economic_price",
            columnDefinition = "double precision"
    )
    private double economicPrice;

    @Column(
            name = "business_price",
            columnDefinition = "double precision"
    )
    private double businessPrice;

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

    public double getEconomicPrice() {
        return economicPrice;
    }

    public void setEconomicPrice(double economicPrice) {
        this.economicPrice = economicPrice;
    }

    public double getBusinessPrice() {
        return businessPrice;
    }

    public void setBusinessPrice(double businessPrice) {
        this.businessPrice = businessPrice;
    }
}
