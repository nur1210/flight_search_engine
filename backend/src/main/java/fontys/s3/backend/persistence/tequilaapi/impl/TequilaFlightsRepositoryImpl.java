package fontys.s3.backend.persistence.tequilaapi.impl;

import fontys.s3.backend.domain.model.FlightParams;
import fontys.s3.backend.persistence.entity.AirportEntity;
import fontys.s3.backend.persistence.entity.FlightEntity;
import fontys.s3.backend.persistence.entity.RouteEntity;
import fontys.s3.backend.persistence.tequilaapi.TequilaAirportsRepository;
import fontys.s3.backend.persistence.tequilaapi.TequilaFlightsRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private static final Logger log = LoggerFactory.getLogger(TequilaFlightsRepositoryImpl.class);
    private TequilaAirportsRepository tequilaAirportsRepository;
    @Value("${tequila.api.key}")
    private String apiKey;
    private String url = "https://api.tequila.kiwi.com/v2/search?fly_from={flyFrom}&fly_to={flyTo}&date_from={dateFrom}&date_to={dateTo}&return_from={returnFrom}&return_to={returnTo}&nights_in_dst_from={minNightsInDestination}&nights_in_dst_to={maxNightsInDestination}&flight_type={flightType}&ret_from_diff_city={returnFromDifferentCity}&ret_to_diff_city={returnToDifferentCity}&one_for_city={onePerDestination}&adults={adults}&selected_cabins={selectedCabins}&only_working_days={onlyWorkingDays}&only_weekends={onlyWeekends}&curr={currency}&locale={language}&max_stopovers={maxStopovers}&max_sector_stopovers={maxSectorStopovers}&limit={limit}";
    private RestTemplate restTemplate;
    private Map<String, String> map;

    @Autowired
    public TequilaFlightsRepositoryImpl(TequilaAirportsRepository tequilaAirportsRepository) {
        this.tequilaAirportsRepository = tequilaAirportsRepository;
    }

    @Override
    public List<FlightEntity> getFlightsInfo(FlightParams params) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            var dateFrom = dateFormat.parse(params.getDateFrom());
            var dateTo = dateFormat.parse(params.getDateTo());

            if (new Date().after(dateFrom)) {
                throw new IllegalArgumentException("DATE_IS_IN_THE_PAST");
            } else if (dateFrom.after(dateTo)) {
                throw new IllegalArgumentException("RETURN_DATE_IS_BEFORE_DEPARTURE_DATE");
            }
        } catch (ParseException e) {
            log.error("DateFrom is not in the correct format");
            return Collections.emptyList();
        }

        restTemplate = new RestTemplate();
        map = new HashMap<>();


        ReflectionUtils.doWithFields(params.getClass(), field -> {
            if (field.get(params) != null) {
                map.put(field.getName(), field.get(params).toString());
            } else {
                map.put(field.getName(), "");
            }
        });

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
                List<RouteEntity> outboundRoutes = new ArrayList<>();
                List<RouteEntity> returnRoutes = new ArrayList<>();
                List<RouteEntity> routes = outboundRoutes;
                for (var route : flight.getRoute()) {
                    if (params.flightType.equals("round")) {
                        extractRoutesFromJSON(routes, route);
                        if (route.getCityCodeTo().equals(params.getFlyTo()) || route.getFlyTo().equals(params.getFlyTo())) {
                            routes = returnRoutes;
                        }
                    } else {
                        extractRoutesFromJSON(routes, route);
                    }
                }

                flights.add(FlightEntity.builder()
                        .outboundRoutes(outboundRoutes)
                        .returnRoutes(returnRoutes)
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

    private void extractRoutesFromJSON(List<RouteEntity> routes, JSONToFlight.Route route) {
        var airportFrom = tequilaAirportsRepository.getAirportByIata(route.getFlyFrom());
        var airportTo = tequilaAirportsRepository.getAirportByIata(route.getFlyTo());
        routes.add(RouteEntity.builder()
                .flightNumber(route.getFlightNumber())
                .airline(route.getAirline())
                .departureAirport(AirportEntity.builder()
                        .iata(airportFrom.getIata())
                        .name(airportFrom.getName())
                        .city(airportFrom.getCity())
                        .cityCode(airportFrom.getCityCode())
                        .country(airportFrom.getCountry())
                        .countryCode(airportFrom.getCountryCode())
                        .build())
                .arrivalAirport(AirportEntity.builder()
                        .iata(airportTo.getIata())
                        .name(airportTo.getName())
                        .city(airportTo.getCity())
                        .cityCode(airportTo.getCityCode())
                        .country(airportTo.getCountry())
                        .countryCode(airportTo.getCountryCode())
                        .build())
                .localDepartureTime(route.getLocalDeparture())
                .utcDepartureTime(route.getUtcDeparture())
                .localArrivalTime(route.getLocalArrival())
                .utcArrivalTime(route.getUtcArrival())
                .build());
    }
}

