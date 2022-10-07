package fontys.s3.backend.persistence.tequilaApi;

import fontys.s3.backend.persistence.entity.AirportEntity;

public interface TequilaAirportsRepository {
    AirportEntity getAirportByCity(String city);
    AirportEntity getAirportByCords(String lat, String lon);
}
