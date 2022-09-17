package fontys.s3.backend.persistence.impl;

import fontys.s3.backend.persistence.FlightRepositoryBasic;
import fontys.s3.backend.persistence.FlightRepositoryCustom;
import fontys.s3.backend.persistence.entity.FlightEntity;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class FlightRepositoryCustomImpl implements FlightRepositoryCustom {
    private final FlightRepositoryBasic flightRepositoryBasic;
    @PersistenceContext
    private EntityManager entityManager;

    public FlightRepositoryCustomImpl(FlightRepositoryBasic flightRepositoryBasic) {
        this.flightRepositoryBasic = flightRepositoryBasic;
    }
    public List<FlightEntity> FindAllFlightsFromOriginCodeToDestinationCode(String departureAirportIATA, String arrivalAirportIATA) {
        return flightRepositoryBasic.findAll()
                .stream()
                .filter(flightEntity -> flightEntity.getDepartureAirport().getIata().equals(departureAirportIATA))
                .filter(flightEntity -> flightEntity.getArrivalAirport().getIata().equals(arrivalAirportIATA))
                .toList();
    }
}
