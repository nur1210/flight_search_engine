package fontys.s3.backend.business.impl;

import fontys.s3.backend.domain.Airport;
import fontys.s3.backend.persistence.entity.AirportEntity;

public class AirportConverter {
    public static Airport convert(AirportEntity airport){
        return Airport.builder()
                .iata(airport.getIata())
                .city(airport.getCity())
                .cityCode(airport.getCityCode())
                .country(airport.getCountry())
                .countryCode(airport.getCountryCode())
                .build();
    }
}
