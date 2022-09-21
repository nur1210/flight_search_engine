package fontys.s3.backend.persistence.tequila.impl;

import fontys.s3.backend.persistence.entity.AirportEntity;
import fontys.s3.backend.persistence.entity.FlightEntity;
import fontys.s3.backend.persistence.tequila.GetFlightInfoRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class GetFlightInfoRepositoryImpl implements GetFlightInfoRepository {
    private static final String API_KEY = "91DK_D7GvsoeTQ8fUDHwIV8jaBfOr5kP";
    private static final Logger LOGGER = LoggerFactory.getLogger(GetFlightInfoRepository.class);
    private String URL = "https://tequila-api.kiwi.com/v2/search?fly_from={fly_from}&fly_to={fly_to}&date_from={date_from}&date_to={date_to}&return_from={return_from}&return_to={return_to}&flight_type={flight_type}&selected_cabins={selected_cabins}&curr={curr}&locale={locale}&max_stopovers={max_stopovers}&max_sector_stopovers={max_sector_stopovers}";
    private RestTemplate restTemplate;

    @Override
    public List<FlightEntity> getFlightsInfo(Map<String, String> params) {

        try {
            List<FlightEntity> flights = new ArrayList<>();

            LOGGER.info("Calling tequilla API for flights info with params {}.", params);
            HttpHeaders headers = new HttpHeaders();
            headers.add("apikey", API_KEY);
            HttpEntity<FlightInfo> entity = new HttpEntity<>(headers);
            ResponseEntity<FlightInfo> responseEntity = restTemplate.exchange(
                    URL,
                    HttpMethod.GET,
                    entity,
                    FlightInfo.class,
                    params);

            FlightInfo flightInfo = responseEntity.getBody();

            LOGGER.info("Calling tequilla API for flights info from {} to {} was succseful.", flightInfo.getData(), flightInfo.getData());


            for (var flight : flightInfo.getData()) {
                for (var route : flight.getRoute()) {
                    flights.add(FlightEntity.builder()
                            .flightNumber(route.getAirline() + route.getFlight_no())
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
                            .price(flight.getFare().getAdults())
                            .availableSeats(flight.getAvailability().getSeats())
                            .build());
                }
            }
            return flights;
        } catch (HttpClientErrorException e) {
            LOGGER.error("Error while calling tequilla API for flights info.", e.getResponseBodyAsString(), e);
            return null;
        }
    }
}

