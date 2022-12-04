package fontys.s3.backend.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAllFlightsFromOriginToDestinationRequest {
    @Min(3)
    @Max(3)
    @JsonProperty("fly_from")
    public String flyFrom;
    @Min(3)
    @Max(3)
    @JsonProperty("fly_to")
    public String flyTo;
    @Min(10)
    @Max(10)
    @JsonProperty("date_from")
    public String dateFrom;
    @Min(10)
    @Max(10)
    @JsonProperty("date_to")
    public String dateTo;
    @Min(10)
    @Max(10)
    @JsonProperty("return_from")
    public String returnFrom;
    @Min(10)
    @Max(10)
    @JsonProperty("return_to")
    public String returnTo;
    @Min(1)
    @Max(1)
    @JsonProperty("flight_type")
    public String flightType;
    @Min(1)
    @Max(1)
    public long adults;
    @Min(1)
    @Max(1)
    @JsonProperty("selected_cabins")
    public String selectedCabins;
    @Min(3)
    @Max(3)
    @JsonProperty("curr")
    public String currency;
    @Min(2)
    @Max(2)
    @JsonProperty("locale")
    public String language;
    @Min(1)
    @Max(1)
    @JsonProperty("max_stopovers")
    public long maxStopovers;
    @Min(1)
    @Max(1)
    @JsonProperty("max_sector_stopovers")
    public long maxSectorStopovers;
}
