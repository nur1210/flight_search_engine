package fontys.s3.backend.persistence.tequilaapi.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Setter
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class JSONToFlight {
    @NonNull
    private Data[] data;


    @Setter
    @Getter
    static class Data {
        private Route[] route;
        private CountryFrom countryFrom;
        private CountryTo countryTo;
        private Fare fare;
        private Availability availability;
        @JsonProperty("deep_link")
        private String deepLink;
    }

    @Setter
    @Getter
    static class Route {
        private String flyFrom;
        private String cityFrom;
        private String cityCodeFrom;
        private String flyTo;
        private String cityTo;
        private String cityCodeTo;
        @JsonProperty("local_departure")
        private Timestamp localDeparture;
        @JsonProperty("utc_departure")
        private Timestamp utcDeparture;
        @JsonProperty("local_arrival")
        private Timestamp localArrival;
        @JsonProperty("utc_arrival")
        private Timestamp utcArrival;
        private String airline;
        @JsonProperty("flight_no")
        private long flightNumber;
    }

    @Setter
    @Getter
    static class CountryFrom {
        private String code;
        private String name;
    }

    @Setter
    @Getter
    static class CountryTo {
        private String code;
        private String name;
    }

    @Setter
    @Getter
    static class Fare {
        private double adults;
    }

    @Setter
    @Getter
    static class Availability {
        private long seats;
    }

    @Setter
    @Getter
    static class DeepLink {
        @JsonProperty("deep_link")
        private String link;
    }
}
