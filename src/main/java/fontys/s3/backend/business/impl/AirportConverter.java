package fontys.s3.backend.business.impl;

import fontys.s3.backend.domain.Airport;
import fontys.s3.backend.persistence.entity.AirportEntity;

public class AirportConverter {
    public static Airport convert(AirportEntity airport){
        return Airport.builder()
                .airportCode(airport.getAirportCode())
                .name(airport.getName())
                .address(AddressConverter.convert(airport.getAddress()))
                .build();
    }
}
