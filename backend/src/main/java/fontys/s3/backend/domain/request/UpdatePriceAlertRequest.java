package fontys.s3.backend.domain.request;

import fontys.s3.backend.persistence.entity.FlightEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePriceAlertRequest {
    @NotNull
    private long priceAlertId;
    private FlightEntity flight;
}
