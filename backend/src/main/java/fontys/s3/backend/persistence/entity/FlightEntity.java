package fontys.s3.backend.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


//@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "flight")
@Table(name = "flights")
public class FlightEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @OneToMany(targetEntity = RouteEntity.class,
            cascade = CascadeType.DETACH)
    @JoinColumn(name = "flight_id")
    private List<RouteEntity> outboundRoutes;

    @OneToMany(targetEntity = RouteEntity.class,
            cascade = CascadeType.DETACH)
    @JoinColumn(name = "flight_id")
    private List<RouteEntity> returnRoutes;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<RouteEntity> getOutboundRoutes() {
        return outboundRoutes;
    }

    public void setOutboundRoutes(List<RouteEntity> outboundRoutes) {
        this.outboundRoutes = outboundRoutes;
    }

    public List<RouteEntity> getReturnRoutes() {
        return returnRoutes;
    }

    public void setReturnRoutes(List<RouteEntity> returnRoutes) {
        this.returnRoutes = returnRoutes;
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
    
}
