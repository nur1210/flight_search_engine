package fontys.s3.backend.persistence.tequilaApi.impl;

import fontys.s3.backend.persistence.entity.AirportEntity;
import fontys.s3.backend.persistence.tequilaApi.TequilaAirportsRepository;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Component
@NoArgsConstructor
public class TequilaAirportsRepositoryImpl implements TequilaAirportsRepository {

    private final String BaseUrl = "https://api.tequila.kiwi.com/locations";
    private static final String API_KEY = "91DK_D7GvsoeTQ8fUDHwIV8jaBfOr5kP";


    @Override
    public AirportEntity getAirportByCity(String city) {

        String url = BaseUrl + "/query?term=" + city + "&location_types=airport&limit=1&active_only=true";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("apikey", API_KEY);
        HttpEntity<JSONToAirport> entity = new HttpEntity<>(headers);
        ResponseEntity<JSONToAirport> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, JSONToAirport.class);
        JSONToAirport airportInfo = responseEntity.getBody();

        var airport = Arrays.stream(airportInfo.getLocations()).findFirst().orElse(null);
        if (airport == null) {
            return null;
        }
        return AirportEntity.builder()
                .iata(airport.getCode())
                .city(airport.getCity().getName())
                .cityCode(airport.getCity().getCode())
                .country(airport.getCountry().getName())
                .countryCode(airport.getCountry().getCode())
                .build();
    }

    @Override
    public AirportEntity getAirportByCords(String lat, String lon) {
        String url = BaseUrl + "/radius?lat=" + lat + "&lon=" + lon + "&radius=250&locale=en-US&location_types=airport&limit=1&active_only=true" ;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("apikey", API_KEY);
        HttpEntity<JSONToAirport> entity = new HttpEntity<>(headers);
        ResponseEntity<JSONToAirport> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, JSONToAirport.class);
        JSONToAirport airportInfo = responseEntity.getBody();

        var airport = Arrays.stream(airportInfo.getLocations()).findFirst().orElse(null);
        if (airport == null) {
            return null;
        }
        return AirportEntity.builder()
                .iata(airport.getCode())
                .city(airport.getCity().getName())
                .cityCode(airport.getCity().getCode())
                .country(airport.getCountry().getName())
                .countryCode(airport.getCountry().getCode())
                .build();
    }
}

