package fontys.s3.backend.persistence.tequilaApi.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.net.URL;
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
        private URL deep_link;
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
        private Timestamp local_departure;
        private Timestamp utc_departure;
        private Timestamp local_arrival;
        private Timestamp utc_arrival;
        private String airline;
        private long flight_no;
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
        private String deep_link;
    }
}
