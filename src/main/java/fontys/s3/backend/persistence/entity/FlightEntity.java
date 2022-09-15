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

    @Column(
            name = "origin_code",
            nullable = false,
            columnDefinition = "TEXT",
            length = 3
    )
    private String originCode;

    @Column(
            name = "destination_code",
            nullable = false,
            columnDefinition = "TEXT",
            length = 3
    )
    private String destinationCode;

    @Column(
            name = "departure_time",
            columnDefinition = "TIMESTAMP"
    )
    private Timestamp flightTime;

    @Column(
            name = "landing_time",
            columnDefinition = "TIMESTAMP"
    )
    private Timestamp landTime;

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

    public String getOriginCode() {
        return originCode;
    }

    public void setOriginCode(String originCode) {
        this.originCode = originCode;
    }

    public String getDestinationCode() {
        return destinationCode;
    }

    public void setDestinationCode(String destinationCode) {
        this.destinationCode = destinationCode;
    }

    public Timestamp getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(Timestamp flightTime) {
        this.flightTime = flightTime;
    }

    public Timestamp getLandTime() {
        return landTime;
    }

    public void setLandTime(Timestamp landTime) {
        this.landTime = landTime;
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
