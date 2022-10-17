package fontys.s3.backend.business;


import fontys.s3.backend.domain.AccessToken;

public interface AccessTokenDecoder {
    AccessToken decode(String accessTokenEncoded);
}
