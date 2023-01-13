package fontys.s3.backend.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    @JsonIgnore
    private String password;
    private boolean verified;
}
