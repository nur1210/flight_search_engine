package fontys.s3.backend.business.impl;

import fontys.s3.backend.domain.Route;
import fontys.s3.backend.persistence.entity.RouteEntity;

public class RouteConverter {
    private RouteConverter() {
    }

    public static Route convert(RouteEntity route) {
        return Route.builder()
                .flightNumber(route.getFlightNumber())
                .airline(route.getAirline())
                .departureAirport(AirportConverter.convert(route.getDepartureAirport()))
                .arrivalAirport(AirportConverter.convert(route.getArrivalAirport()))
                .localDepartureTime(route.getLocalDepartureTime())
                .utcDepartureTime(route.getUtcDepartureTime())
                .localArrivalTime(route.getLocalArrivalTime())
                .utcArrivalTime(route.getUtcArrivalTime())
                .build();
    }
}
