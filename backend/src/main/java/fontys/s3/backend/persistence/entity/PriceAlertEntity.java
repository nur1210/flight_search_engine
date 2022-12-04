package fontys.s3.backend.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "price_alert")
public class PriceAlertEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_price_alert",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "price_alert_id")
    )
    private List<UserEntity> users;

    @Column(name = "fly_from")
    private String flyFrom;

    @Column(name = "fly_to")
    private String flyTo;

    @Column(name = "date_from")
    private Date dateFrom;

    @Column(name = "date_to")
    private Date dateTo;

    @Column(name = "flight_type")
    private String flightType;

    @Column(name = "passengers")
    private long passengers;

    @Column(name = "cabin")
    private String cabinClass;

    @Column(name = "curr")
    private String currency;

    @Column(name = "local")
    private String locale;

    @Column(name = "lowest_price")
    private double lowestPrice;

    @OneToOne(targetEntity = FlightEntity.class,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "flight_id")
    private FlightEntity currentFlight;

    public long getId() {
        return id;
    }

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> user) {
        this.users = user;
    }

    public String getFlyFrom() {
        return flyFrom;
    }

    public void setFlyFrom(String flyFrom) {
        this.flyFrom = flyFrom;
    }

    public String getFlyTo() {
        return flyTo;
    }

    public void setFlyTo(String flyTo) {
        this.flyTo = flyTo;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public String getFlightType() {
        return flightType;
    }

    public void setFlightType(String flightType) {
        this.flightType = flightType;
    }

    public long getPassengers() {
        return passengers;
    }

    public void setPassengers(long passengers) {
        this.passengers = passengers;
    }

    public String getCabinClass() {
        return cabinClass;
    }

    public void setCabinClass(String cabinClass) {
        this.cabinClass = cabinClass;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public double getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(double lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public FlightEntity getCurrentFlight() {
        return currentFlight;
    }

    public void setCurrentFlight(FlightEntity currentFlight) {
        this.currentFlight = currentFlight;
    }
}
