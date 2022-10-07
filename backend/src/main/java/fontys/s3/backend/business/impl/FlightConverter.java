package fontys.s3.backend.business.impl;

import fontys.s3.backend.domain.Flight;
import fontys.s3.backend.persistence.entity.FlightEntity;

final class FlightConverter {
    private FlightConverter() {
    }

    public static Flight convert(FlightEntity flight) {
        return Flight.builder()
                .id(flight.getId())
                .route(flight.getRoute().stream().map(RouteConverter::convert).toList())
                .price(flight.getPrice())
                .availableSeats(flight.getAvailableSeats())
                .deepLink(flight.getDeepLink())
                .build();
    }
}
