package fontys.s3.backend.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAdvanceSearchFlightsRequest {
    @NotEmpty
    @JsonProperty("fly_from")
    public String flyFrom;

    @NotEmpty
    @JsonProperty("fly_to")
    public String flyTo;

    @NotEmpty
    @JsonProperty("date_from")
    public String dateFrom;

    @NotEmpty
    @JsonProperty("date_to")
    public String dateTo;

    @NotEmpty
    @JsonProperty("return_from")
    public String returnFrom;

    @NotEmpty
    @JsonProperty("return_to")
    public String returnTo;
    @Min(1)
    @JsonProperty("nights_in_dst_from")
    public long minNightsInDestination;
    @Max(14)
    @JsonProperty("nights_in_dst_to")
    public long maxNightsInDestination;

    @NotEmpty
    @JsonProperty("flight_type")
    public String flightType;
    @Min(0)
    @Max(1)
    @JsonProperty("one_for_city")
    public long onePerDestination;

    @Min(1)
    @Max(9)
    public long adults;

    @NotEmpty
    @JsonProperty("selected_cabins")
    public String selectedCabins;
    @JsonProperty("only_working_days")
    public boolean onlyWorkingDays;

    @JsonProperty("only_weekends")
    public boolean onlyWeekends;

    @NotEmpty
    @JsonProperty("curr")
    public String currency;

    @NotEmpty
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
    @Max(20)
    public long limit;
}
