package fontys.s3.backend.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "address")
@Table(name = "addresses")
public class AddressEntity {
    @SequenceGenerator(
            name = "address_sequence",
            sequenceName = "address_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "address_sequence"
    )
    @Id
    @Column(
            name = "id",
            updatable = false
    )
    private long id;

    @Column(
            name = "street",
            columnDefinition = "TEXT"
    )
    private String street;

    @Column(
            name = "city",
            columnDefinition = "TEXT"
    )
    private String city;

    @Column(
            name = "country",
            columnDefinition = "TEXT"
    )
    private String country;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
