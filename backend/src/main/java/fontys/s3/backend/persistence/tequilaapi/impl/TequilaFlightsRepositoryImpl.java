package fontys.s3.backend.persistence.tequilaapi.impl;

import fontys.s3.backend.persistence.entity.AirportEntity;
import fontys.s3.backend.persistence.entity.FlightEntity;
import fontys.s3.backend.persistence.entity.RouteEntity;
import fontys.s3.backend.persistence.tequilaapi.TequilaFlightsRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class TequilaFlightsRepositoryImpl implements TequilaFlightsRepository {
    @Value("${tequila.api.key}")
    private String apiKey;
    private String url = "https://tequila-api.kiwi.com/v2/search?fly_from={fly_from}&fly_to={fly_to}&date_from={date_from}&date_to={date_to}&return_from={return_from}&return_to={return_to}&flight_type={flight_type}&adults={adults}&selected_cabins={selected_cabins}&curr={curr}&locale={locale}&max_stopovers={max_stopovers}&max_sector_stopovers={max_sector_stopovers}";

    @Override
    public List<FlightEntity> getFlightsInfo(Map<String, Object> params) {
        if (new Date().after((Date)params.get("date_from"))) {
            throw new IllegalArgumentException("DATE_IS_IN_THE_PAST");
        }
        else if (((Date) params.get("date_from")).before((Date)params.get("return_from"))) {
            throw new IllegalArgumentException("RETURN_DATE_IS_BEFORE_DEPARTURE_DATE");
        }
        RestTemplate restTemplate = new RestTemplate();
        try {
            List<FlightEntity> flights = new ArrayList<>();

            HttpHeaders headers = new HttpHeaders();
            headers.add("apikey", apiKey);
            HttpEntity<JSONToFlight> entity = new HttpEntity<>(headers);
            ResponseEntity<JSONToFlight> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    JSONToFlight.class,
                    params);

            JSONToFlight flightInfo = responseEntity.getBody();


            for (var flight : Objects.requireNonNull(flightInfo).getData()) {
                List<RouteEntity> routes = new ArrayList<>();
                for (var route : flight.getRoute()) {
                    routes.add(RouteEntity.builder()
                            .flightNumber(route.getFlightNumber())
                            .airline(route.getAirline())
                            .departureAirport(AirportEntity.builder()
                                    .iata(route.getFlyFrom())
                                    .city(route.getCityFrom())
                                    .cityCode(route.getCityCodeFrom())
                                    .country(flight.getCountryFrom().getName())
                                    .countryCode(flight.getCountryFrom().getCode())
                                    .build())
                            .arrivalAirport(AirportEntity.builder()
                                    .iata(route.getFlyTo())
                                    .city(route.getCityTo())
                                    .cityCode(route.getCityCodeTo())
                                    .country(flight.getCountryTo().getName())
                                    .countryCode(flight.getCountryTo().getCode())
                                    .build())
                            .localDepartureTime(route.getLocalDeparture())
                            .utcDepartureTime(route.getUtcDeparture())
                            .localArrivalTime(route.getLocalArrival())
                            .utcArrivalTime(route.getUtcArrival())
                            .build());
                }
                flights.add(FlightEntity.builder()
                        .route(routes)
                        .price(flight.getFare().getAdults())
                        .availableSeats(flight.getAvailability().getSeats())
                        .deepLink(flight.getDeepLink())
                        .build());
            }
            return flights;
        } catch (HttpClientErrorException e) {
            return Collections.emptyList();
        }
    }
}

