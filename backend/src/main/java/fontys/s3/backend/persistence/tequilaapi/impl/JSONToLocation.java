package fontys.s3.backend.persistence.tequilaapi.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class JSONToLocation {
    private Locations[] locations;

    @Setter
    @Getter
    static class Locations {
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
