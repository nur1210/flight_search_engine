package fontys.s3.backend.persistence.tequilaapi;

import fontys.s3.backend.persistence.entity.FlightEntity;

import java.util.List;
import java.util.Map;

public interface TequilaFlightsRepository {
    List<FlightEntity> getFlightsInfo(Map<String, Object> params);
}
