package fontys.s3.backend.business;

import fontys.s3.backend.domain.RefreshToken;

import java.util.Map;

public interface RefreshTokenEncoder {
    Map<String, String> encode(RefreshToken refreshToken);

}
