package fontys.s3.backend.domain.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserResponse {
    private long userId;
}
