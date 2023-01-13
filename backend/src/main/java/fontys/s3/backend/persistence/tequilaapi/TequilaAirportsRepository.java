package fontys.s3.backend.persistence.tequilaapi;

import fontys.s3.backend.domain.model.Destination;
import fontys.s3.backend.persistence.entity.AirportEntity;

import java.util.List;

public interface TequilaAirportsRepository {
    AirportEntity getAirportByCity(String city);
    AirportEntity getAirportByIata(String iata);
    AirportEntity getAirportByCords(String lat, String lon);
    List<Destination> getTopTenDestinationsFromOrigin(String city);
}
