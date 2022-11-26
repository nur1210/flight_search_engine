package fontys.s3.backend.persistence.tequilaapi.impl;

import fontys.s3.backend.domain.FlightParams;
import fontys.s3.backend.persistence.entity.AirportEntity;
import fontys.s3.backend.persistence.entity.FlightEntity;
import fontys.s3.backend.persistence.entity.RouteEntity;
import fontys.s3.backend.persistence.tequilaapi.TequilaFlightsRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class TequilaFlightsRepositoryImpl implements TequilaFlightsRepository {
    @Value("${tequila.api.key}")
    private String apiKey;
    private String url = "https://tequila-api.kiwi.com/v2/search?fly_from={flyFrom}&fly_to={flyTo}&date_from={dateFrom}&date_to={dateTo}&return_from={returnFrom}&return_to={returnTo}&flight_type={flightType}&adults={adults}&selected_cabins={selectedCabins}&curr={currency}&locale={language}&max_stopovers={maxStopovers}&max_sector_stopovers={maxSectorStopovers}";

    private static final Logger log = LoggerFactory.getLogger(TequilaFlightsRepositoryImpl.class);
    @Override
    public List<FlightEntity> getFlightsInfo(FlightParams params) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            var dateFrom = dateFormat.parse(params.getDateFrom());
            var dateTo = dateFormat.parse(params.getDateTo());

            if (new Date().after(dateFrom)) {
                throw new IllegalArgumentException("DATE_IS_IN_THE_PAST");
            }
            else if (dateFrom.after(dateTo)) {
                throw new IllegalArgumentException("RETURN_DATE_IS_BEFORE_DEPARTURE_DATE");
            }
        }
        catch (ParseException e) {
            log.error("DateFrom is not in the correct format");
            return Collections.emptyList();
        }

        RestTemplate restTemplate = new RestTemplate();

                Map<String, Object> map = new HashMap<>();

        ReflectionUtils.doWithFields(params.getClass(), field ->
                map.put(field.getName(), field.get(params)));
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
                    map);

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

