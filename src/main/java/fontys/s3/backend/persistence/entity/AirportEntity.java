package fontys.s3.backend.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "airport")
@Table(name = "airports")
public class AirportEntity {
    @Id
    @Column(name = "airport_code",
            nullable = false,
            columnDefinition = "TEXT",
            length = 3)
    private String airportCode;

    @Column(name = "name",
            nullable = false,
            columnDefinition = "TEXT")
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private AddressEntity address;

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }
}
