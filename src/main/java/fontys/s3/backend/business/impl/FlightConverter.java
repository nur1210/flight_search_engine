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
                .departureAirport(AirportConverter.convert(flight.getDepartureAirport()))
                .arrivalAirport(AirportConverter.convert(flight.getArrivalAirport()))
                .localDepartureTime(flight.getLocalDepartureTime())
                .utcDepartureTime(flight.getUtcDepartureTime())
                .localArrivalTime(flight.getLocalArrivalTime())
                .utcArrivalTime(flight.getUtcArrivalTime())
                .economicPrice(flight.getEconomicPrice())
                .businessPrice(flight.getBusinessPrice())
                .build();
    }
}
