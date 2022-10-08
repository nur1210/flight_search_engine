package fontys.s3.backend.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;


//@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "flight")
@Table(name = "flights")
public class FlightEntity {
    @SequenceGenerator(
            name = "flight_sequence",
            sequenceName = "flight_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "flight_sequence"
    )
    @Id
    @Column(
            name = "id",
            updatable = false
    )
    private long id;

    @OneToMany(targetEntity = RouteEntity.class,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "flight_id")
    private List<RouteEntity> route;
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

    @Column(
            name = "deep_link",
            columnDefinition = "text"
    )
    private String deepLink;

    @ManyToMany(mappedBy = "userFlights")
    private List<UserEntity> passengers;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<RouteEntity> getRoute() {
        return route;
    }

    public void setRoute(List<RouteEntity> route) {
        this.route = route;
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

    public String getDeepLink() {
        return deepLink;
    }

    public void setDeepLink(String deepLink) {
        this.deepLink = deepLink;
    }

    public List<UserEntity> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<UserEntity> passengers) {
        this.passengers = passengers;
    }
}
