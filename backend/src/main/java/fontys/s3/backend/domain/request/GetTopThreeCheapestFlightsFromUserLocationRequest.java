package fontys.s3.backend.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTopThreeCheapestFlightsFromUserLocationRequest {

    @JsonProperty("fly_from")
    public String flyFrom;

    @JsonProperty("date_from")
    public String dateFrom;

    @JsonProperty("date_to")
    public String dateTo;

    @JsonProperty("return_from")
    public String returnFrom;

    @JsonProperty("return_to")
    public String returnTo;
    @Min(1)
    @JsonProperty("nights_in_dst_from")
    public long minNightsInDestination;
    @Max(9)
    @JsonProperty("nights_in_dst_to")
    public long maxNightsInDestination;

    @JsonProperty("flight_type")
    public String flightType;
    @JsonProperty("ret_from_diff_city")
    public boolean returnFromDifferentCity;
    @JsonProperty("ret_to_diff_city")
    public boolean returnToDifferentCity;
    @JsonProperty("one_for_city")
    public long onePerDestination;

    @Min(1)
    @Max(9)
    public long adults;
    @JsonProperty("selected_cabins")
    public String selectedCabins;
    @JsonProperty("only_working_days")
    public boolean onlyWorkingDays;
    @JsonProperty("only_weekends")
    public boolean onlyWeekends;

    @JsonProperty("curr")
    public String currency;
    @JsonProperty("locale")
    public String language;
    @Min(0)
    @Max(2)
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
