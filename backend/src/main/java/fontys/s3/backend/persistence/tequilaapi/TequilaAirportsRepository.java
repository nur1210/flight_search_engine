package fontys.s3.backend.persistence.tequilaapi;

import fontys.s3.backend.persistence.entity.AirportEntity;

public interface TequilaAirportsRepository {
    AirportEntity getAirportByCity(String city);
    AirportEntity getAirportByIata(String iata);
    AirportEntity getAirportByCords(String lat, String lon);
}
