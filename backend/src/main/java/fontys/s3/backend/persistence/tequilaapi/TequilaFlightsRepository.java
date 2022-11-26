package fontys.s3.backend.persistence.tequilaapi;

import fontys.s3.backend.domain.FlightParams;
import fontys.s3.backend.persistence.entity.FlightEntity;

import java.util.List;

public interface TequilaFlightsRepository {
    List<FlightEntity> getFlightsInfo(FlightParams params);
}
