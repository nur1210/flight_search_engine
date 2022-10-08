package fontys.s3.backend.persistence.tequilaApi.impl;

import fontys.s3.backend.persistence.entity.AirportEntity;
import fontys.s3.backend.persistence.entity.FlightEntity;
import fontys.s3.backend.persistence.entity.RouteEntity;
import fontys.s3.backend.persistence.tequilaApi.TequilaFlightsRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class TequilaFlightsRepositoryImpl implements TequilaFlightsRepository {
    private static final String API_KEY = "91DK_D7GvsoeTQ8fUDHwIV8jaBfOr5kP";
    private String URL = "https://tequila-api.kiwi.com/v2/search?fly_from={fly_from}&fly_to={fly_to}&date_from={date_from}&date_to={date_to}&return_from={return_from}&return_to={return_to}&flight_type={flight_type}&adults={adults}&selected_cabins={selected_cabins}&curr={curr}&locale={locale}&max_stopovers={max_stopovers}&max_sector_stopovers={max_sector_stopovers}";

    @Override
    public List<FlightEntity> getFlightsInfo(Map<String, Object> params) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            List<FlightEntity> flights = new ArrayList<>();

            HttpHeaders headers = new HttpHeaders();
            headers.add("apikey", API_KEY);
            HttpEntity<JSONToFlight> entity = new HttpEntity<>(headers);
            ResponseEntity<JSONToFlight> responseEntity = restTemplate.exchange(
                    URL,
                    HttpMethod.GET,
                    entity,
                    JSONToFlight.class,
                    params);

            JSONToFlight flightInfo = responseEntity.getBody();


            for (var flight : flightInfo.getData()) {
                List<RouteEntity> routes = new ArrayList<>();
                for (var route : flight.getRoute()) {
                    routes.add(RouteEntity.builder()
                            .flightNumber(route.getFlight_no())
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
                            .localDepartureTime(route.getLocal_departure())
                            .utcDepartureTime(route.getUtc_departure())
                            .localArrivalTime(route.getLocal_arrival())
                            .utcArrivalTime(route.getUtc_arrival())
                            .build());
                }
                flights.add(FlightEntity.builder()
                        .route(routes)
                        .price(flight.getFare().getAdults())
                        .availableSeats(flight.getAvailability().getSeats())
                        .deepLink(flight.getDeep_link())
                        .build());
            }
            return flights;
        } catch (HttpClientErrorException e) {
            return null;
        }
    }
}

