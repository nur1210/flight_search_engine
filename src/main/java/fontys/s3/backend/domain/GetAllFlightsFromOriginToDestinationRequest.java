package fontys.s3.backend.domain;

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
    public String fly_from;
    @Min(3)
    @Max(3)
    public String fly_to;
    @Min(10)
    @Max(10)
    public String date_from;
    @Min(10)
    @Max(10)
    public String date_to;
    @Min(10)
    @Max(10)
    public String return_from;
    @Min(10)
    @Max(10)
    public String return_to;
    @Min(1)
    @Max(1)
    public String flight_type;
    @Min(1)
    @Max(1)
    public long adults;
    @Min(1)
    @Max(1)
    public String selected_cabins;
    @Min(3)
    @Max(3)
    public String curr;
    @Min(2)
    @Max(2)
    public String locale;
    @Min(1)
    @Max(1)
    public long max_stopovers;
    @Min(1)
    @Max(1)
    public long max_sector_stopovers;
}
