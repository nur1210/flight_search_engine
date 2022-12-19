package fontys.s3.backend.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAllFlightsFromOriginToDestinationRequest {

    @NotBlank
    @JsonProperty("fly_from")
    public String flyFrom;

    @NotBlank
    @JsonProperty("fly_to")
    public String flyTo;

    @NotBlank
    @JsonProperty("date_from")
    public String dateFrom;

    @NotBlank
    @JsonProperty("date_to")
    public String dateTo;

    @NotBlank
    @JsonProperty("return_from")
    public String returnFrom;

    @NotBlank
    @JsonProperty("return_to")
    public String returnTo;

    @NotBlank
    @JsonProperty("flight_type")
    public String flightType;

    @Min(1)
    @Max(9)
    public long adults;

    @NotBlank
    @JsonProperty("selected_cabins")
    public String selectedCabins;

    @NotBlank
    @JsonProperty("curr")
    public String currency;
    @NotBlank
    @JsonProperty("locale")
    public String language;
    @Min(0)
    @Max(4)
    @JsonProperty("max_stopovers")
    public long maxStopovers;
    @Min(0)
    @Max(2)
    @JsonProperty("max_sector_stopovers")
    public long maxSectorStopovers;
    @Min(1)
    @Max(200)
    public long limit;
}
