package fontys.s3.backend.business.impl.converter;

import fontys.s3.backend.domain.model.Flight;
import fontys.s3.backend.persistence.entity.FlightEntity;

import java.util.Objects;

public final class FlightConverter {
    private FlightConverter() {
    }

    public static Flight convert(FlightEntity flight) {
        return Flight.builder()
                .id(flight.getId())
                .outboundRoutes(flight.getOutboundRoutes().stream().map(RouteConverter::convert).toList())
                .returnRoutes(flight.getReturnRoutes().stream().filter(Objects::nonNull).map(RouteConverter::convert).toList())
                .price(flight.getPrice())
                .availableSeats(flight.getAvailableSeats())
                .deepLink(flight.getDeepLink())
                .build();
    }
}
