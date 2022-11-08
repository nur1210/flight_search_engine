package fontys.s3.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePriceAlertRequest {
    private String userEmail;
    private String flyFrom;
    private String flyTo;
    private String dateFrom;
    private String dateTo;
    private String flightType;
    private long passengers;
    private String cabinClass;
    private String currency;
    private String locale;
}
