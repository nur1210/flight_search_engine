package fontys.s3.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateFlightRequest {
    private long flight_no;
    private String airline;
    private String flyFrom;
    private String cityFrom;
    private String cityCodeFrom;
    private JSONCountryFrom countryFrom;
    private String flyTo;
    private String cityTo;
    private String cityCodeTo;
    private JSONCountryTo countryTo;
    private Timestamp local_departure;
    private Timestamp utc_departure;
    private Timestamp local_arrival;
    private Timestamp utc_arrival;
    private double economicPrice;
    private double businessPrice;
}
