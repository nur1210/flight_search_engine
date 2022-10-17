package fontys.s3.backend.persistence.tequilaapi;

import fontys.s3.backend.persistence.entity.AirportEntity;

public interface TequilaAirportsRepository {
    AirportEntity getAirportByCity(String city);
    AirportEntity getAirportByCords(String lat, String lon);
}
