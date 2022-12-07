package fontys.s3.backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightParams {

    public String flyFrom;

    public String flyTo;

    public String dateFrom;

    public String dateTo;
    public String returnFrom;
    public String returnTo;
    public String minNightsInDestination;
    public String maxNightsInDestination;
    public String returnFromDifferentCity;
    public String returnToDifferentCity;
    public String resultsPerDestination;
    public String onlyWorkingDays;
    public String onlyWeekends;
    public String limit;

    public String flightType;

    public String adults;
    public String selectedCabins;

    public String currency;
    public String language;
    public String maxStopovers;
    public String maxSectorStopovers;
}
