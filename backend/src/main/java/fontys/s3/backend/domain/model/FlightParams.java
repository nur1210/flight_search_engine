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

    public String flightType;

    public String adults;

    public String selectedCabins;

    public String currency;

    public String language;

    public String maxStopovers;

    public String maxSectorStopovers;
}
