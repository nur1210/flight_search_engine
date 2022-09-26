package fontys.s3.backend.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "airport")
@Table(name = "airports")
public class AirportEntity {
    @Id
    @Column(name = "iata",
            nullable = false,
            columnDefinition = "TEXT",
            length = 3)
    private String iata;

    @Column(name = "city",
            nullable = false,
            columnDefinition = "TEXT")
    private String city;

    @Column(name = "city_code",
            nullable = false,
            columnDefinition = "TEXT",
            length = 3)
    private String cityCode;

    @Column(name = "country",
            nullable = false,
            columnDefinition = "TEXT")
    private String country;

    @Column(name = "country_code",
            nullable = false,
            columnDefinition = "TEXT",
            length = 2)
    private String countryCode;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "arrivalAirport",
            orphanRemoval = true)
    private List<RouteEntity> arrivalRoutes;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "departureAirport",
            orphanRemoval = true)
    private List<RouteEntity> departureRoutes;


    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
