package fontys.s3.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateFlightRequest {
    @NotBlank
    private String flightNumber;
    @NotBlank
    private String airline;
    private String originCode;
    private String destinationCode;
    private Timestamp flightTime;
    private Timestamp landTime;
    private double economicPrice;
    private double businessPrice;
}
