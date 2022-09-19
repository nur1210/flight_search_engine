package fontys.s3.backend.domain;

import fontys.s3.backend.persistence.entity.JSONCountryFromEntity;
import fontys.s3.backend.persistence.entity.JSONCountryToEntity;
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
    private JSONCountryFromEntity countryFrom;
    private String flyTo;
    private String cityTo;
    private String cityCodeTo;
    private JSONCountryToEntity countryTo;
    private Timestamp local_departure;
    private Timestamp utc_departure;
    private Timestamp local_arrival;
    private Timestamp utc_arrival;
    private double economicPrice;
    private double businessPrice;
}
