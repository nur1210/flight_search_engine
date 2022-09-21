package fontys.s3.backend.persistence.tequila;

import fontys.s3.backend.persistence.entity.FlightEntity;

import java.util.List;
import java.util.Map;

public interface GetFlightInfoRepository {
    List<FlightEntity> getFlightsInfo(Map<String, String> params);
}
