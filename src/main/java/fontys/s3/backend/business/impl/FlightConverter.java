package fontys.s3.backend.business.impl;

import fontys.s3.backend.domain.Flight;
import fontys.s3.backend.persistence.entity.FlightEntity;

final class FlightConverter {
    private FlightConverter() {
    }

    public static Flight convert(FlightEntity flight) {
        return Flight.builder()
                .flightNumber(flight.getFlightNumber())
                .airline(flight.getAirline())
                .originCode(flight.getOriginCode())
                .destinationCode(flight.getDestinationCode())
                .flightTime(flight.getFlightTime())
                .landTime(flight.getLandTime())
                .economicPrice(flight.getEconomicPrice())
                .businessPrice(flight.getBusinessPrice())
                .build();
    }
}
