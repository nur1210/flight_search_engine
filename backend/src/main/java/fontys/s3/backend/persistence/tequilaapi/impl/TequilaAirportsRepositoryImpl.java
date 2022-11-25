package fontys.s3.backend.persistence.tequilaapi.impl;

import fontys.s3.backend.business.exception.InvalidAirportException;
import fontys.s3.backend.persistence.entity.AirportEntity;
import fontys.s3.backend.persistence.tequilaapi.TequilaAirportsRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    private static final String BASE_URL = "https://api.tequila.kiwi.com/locations";
    @Value("${tequila.api.key}")
    private String apiKey;


    @Override
    public AirportEntity getAirportByCity(String city) {
        String url = BASE_URL + "/query?term=" + city + "&location_types=airport&limit=1&active_only=true";
        return getAirportEntity(url);
    }

    @Override
    public AirportEntity getAirportByCords(String lat, String lon) {
        String url = BASE_URL + "/radius?lat=" + lat + "&lon=" + lon + "&radius=250&locale=en-US&location_types=airport&limit=1&active_only=true" ;
        return getAirportEntity(url);
    }

    private AirportEntity getAirportEntity(String url) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("apikey", apiKey);
        HttpEntity<JSONToAirport> entity = new HttpEntity<>(headers);
        ResponseEntity<JSONToAirport> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, JSONToAirport.class);
        JSONToAirport airportInfo = responseEntity.getBody();

        assert airportInfo != null;
        var airport = Arrays.stream(airportInfo.getLocations()).findFirst().orElseThrow(InvalidAirportException::new);

        return AirportEntity.builder()
                .iata(airport.getCode())
                .name(airport.getName())
                .city(airport.getCity().getName())
                .cityCode(airport.getCity().getCode())
                .country(airport.getCity().getCountry().getName())
                .countryCode(airport.getCity().getCountry().getCode())
                .build();
    }
}

