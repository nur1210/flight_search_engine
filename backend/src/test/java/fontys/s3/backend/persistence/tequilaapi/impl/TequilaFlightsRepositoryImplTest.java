package fontys.s3.backend.persistence.tequilaapi.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class TequilaFlightsRepositoryImplTest {

    @Autowired
    private TestRestTemplate restTemplate;
    private static final String API_KEY = "91DK_D7GvsoeTQ8fUDHwIV8jaBfOr5kP";
    private String url = "https://tequila-api.kiwi.com/v2/search?fly_from={fly_from}&fly_to={fly_to}&date_from={date_from}&date_to={date_to}&return_from={return_from}&return_to={return_to}&flight_type={flight_type}&adults={adults}&selected_cabins={selected_cabins}&curr={curr}&locale={locale}&max_stopovers={max_stopovers}&max_sector_stopovers={max_sector_stopovers}";
    private final Map<String, String> params = new HashMap<>();

    @BeforeEach
    void setUp() {
        params.put("fly_from", "AMS");
        params.put("fly_to", "FRA");
        params.put("date_from", "2023-01-10");
        params.put("date_to", "2023-01-10");
        params.put("return_from", "2023-04-10");
        params.put("return_to", "2023-04-10");
        params.put("flight_type", "round");
        params.put("adults", "1");
        params.put("selected_cabins", "M");
        params.put("curr", "EUR");
        params.put("locale", "en");
        params.put("max_stopovers", "0");
        params.put("max_sector_stopovers", "0");
    }

    @Test
    void getFlightsInfoWithRequiredParams() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("apikey", API_KEY);
        HttpEntity<JSONToFlight> entity = new HttpEntity<>(headers);
        ResponseEntity<JSONToFlight> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                JSONToFlight.class,
                params);

        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    void getFlightsInfoWithInvalidDepartureDate() {

        params.replace("date_from", "01/01/2022");

        HttpHeaders headers = new HttpHeaders();
        headers.add("apikey", API_KEY);
        HttpEntity<JSONToFlight> entity = new HttpEntity<>(headers);
        ResponseEntity<JSONToFlight> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                JSONToFlight.class,
                params);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            throw new IllegalArgumentException("DATE_IS_IN_THE_PAST");
        });
    }

    @Test
    void getFlightsInfoWithInvalidReturnDate() {

        params.replace("return_from", "01/01/2022");

        HttpHeaders headers = new HttpHeaders();
        headers.add("apikey", API_KEY);
        HttpEntity<JSONToFlight> entity = new HttpEntity<>(headers);
        ResponseEntity<JSONToFlight> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                JSONToFlight.class,
                params);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            throw new IllegalArgumentException("RETURN_DATE_IS_BEFORE_DEPARTURE_DATE");
        });
    }

    @Test
    void getFlightsInfoWithMissingParams() {
        params.remove("fly_from");
        params.put("fly_from", "");

        HttpHeaders headers = new HttpHeaders();
        headers.add("apikey", API_KEY);
        HttpEntity<JSONToFlight> entity = new HttpEntity<>(headers);
        ResponseEntity<JSONToFlight> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                JSONToFlight.class,
                params);

        Assertions.assertEquals(400, responseEntity.getStatusCodeValue());
    }

    @Test
    void getFlightsInfoWithMissingApiKey() {

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<JSONToFlight> entity = new HttpEntity<>(headers);
        ResponseEntity<JSONToFlight> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                JSONToFlight.class,
                params);

        Assertions.assertEquals(403, responseEntity.getStatusCodeValue());
    }
}