package fontys.s3.backend.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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

    @Column(
            name = "name",
            columnDefinition = "TEXT"
    )
    private String name;

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
            columnDefinition = "TEXT")
    private String country;

    @Column(name = "country_code",
            columnDefinition = "TEXT",
            length = 2)
    private String countryCode;


    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
