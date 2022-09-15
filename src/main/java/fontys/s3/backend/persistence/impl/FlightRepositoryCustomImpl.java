package fontys.s3.backend.persistence.impl;

import fontys.s3.backend.persistence.FlightRepositoryBasic;
import fontys.s3.backend.persistence.FlightRepositoryCustom;
import fontys.s3.backend.persistence.entity.FlightEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class FlightRepositoryCustomImpl implements FlightRepositoryCustom {
    private final FlightRepositoryBasic flightRepositoryBasic;
    @PersistenceContext
    private EntityManager entityManager;

    public FlightRepositoryCustomImpl(FlightRepositoryBasic flightRepositoryBasic) {
        this.flightRepositoryBasic = flightRepositoryBasic;
    }
    public List<FlightEntity> FindAllFlightsFromOriginCodeToDestinationCode(String originCode, String destinationCode) {
        return flightRepositoryBasic.findAll()
                .stream()
                .filter(flightEntity -> flightEntity.getOriginCode().equals(originCode))
                .filter(flightEntity -> flightEntity.getDestinationCode().equals(destinationCode))
                .toList();
    }
}
