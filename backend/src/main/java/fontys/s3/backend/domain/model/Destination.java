package fontys.s3.backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Destination {
    private String city;
    private String cityCode;
    private String country;
    private String countryCode;
}
