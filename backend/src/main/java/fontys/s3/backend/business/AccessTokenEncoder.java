package fontys.s3.backend.business;

import fontys.s3.backend.domain.AccessToken;

public interface AccessTokenEncoder {
    String encode(AccessToken accessToken);

}
