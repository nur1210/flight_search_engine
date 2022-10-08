package fontys.s3.backend.persistence.tequilaApi.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class JSONToAirport {
    private Locations[] locations;

    @Setter
    @Getter
    static class Locations {
        private String code;
        private String name;
        private City city;
    }

    @Setter
    @Getter
    static class City {
        private String name;
        private String code;
        private Country country;
    }

    @Setter
    @Getter
    static class Country {
        private String name;
        private String code;
    }
}
