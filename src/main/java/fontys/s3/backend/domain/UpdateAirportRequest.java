package fontys.s3.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAirportRequest {
    @NotBlank
    private String initials;
    @NotBlank
    private String name;
}
