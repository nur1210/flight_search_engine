package fontys.s3.backend.business;

import fontys.s3.backend.domain.AccessToken;

import java.util.Map;

public interface AccessTokenEncoder {
    Map<String, String> encode(AccessToken accessToken);

}
