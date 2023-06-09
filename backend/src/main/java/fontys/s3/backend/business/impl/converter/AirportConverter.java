package fontys.s3.backend.business.impl.converter;

import fontys.s3.backend.domain.model.Airport;
import fontys.s3.backend.persistence.entity.AirportEntity;

public final class AirportConverter {
    private AirportConverter() {
    }
    public static Airport convert(AirportEntity airport){
        return Airport.builder()
                .iata(airport.getIata())
                .name(airport.getName())
                .city(airport.getCity())
                .cityCode(airport.getCityCode())
                .country(airport.getCountry())
                .countryCode(airport.getCountryCode())
                .build();
    }
}
