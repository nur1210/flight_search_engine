package fontys.s3.backend.persistence;

import fontys.s3.backend.persistence.entity.FlightEntity;

import java.util.List;

public interface FlightRepositoryCustom {
    List<FlightEntity> FindAllFlightsFromOriginCodeToDestinationCode(String originCode, String destinationCode);
}
