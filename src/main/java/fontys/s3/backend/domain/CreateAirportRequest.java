package fontys.s3.backend.domain;

import fontys.s3.backend.persistence.entity.AddressEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAirportRequest {
    private String airportCode;
    private String name;
    private AddressEntity address;
}
