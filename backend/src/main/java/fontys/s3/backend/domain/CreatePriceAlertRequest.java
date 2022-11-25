package fontys.s3.backend.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePriceAlertRequest {
    @NotNull
    @JsonProperty("origin")
    private String flyFrom;
    @NotNull
    @JsonProperty("destination")
    private String flyTo;
    @NotNull
    @JsonProperty("departureDate")
    private Date dateFrom;
    @JsonProperty("returnDate")
    private Date dateTo;
    @NotNull
    @JsonProperty("flightType")
    private String flightType;
    @Min(1)
    @Max(9)
    @JsonProperty("adults")
    private long passengers;
    @NotNull
    @JsonProperty("travelClass")
    private String cabinClass;
    @Builder.Default
    private String currency = "EUR";
    @Builder.Default
    private String locale = "en";
}
