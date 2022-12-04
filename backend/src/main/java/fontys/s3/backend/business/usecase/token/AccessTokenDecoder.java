package fontys.s3.backend.business.usecase.token;


import fontys.s3.backend.domain.model.AccessToken;

public interface AccessTokenDecoder {
    AccessToken decode(String accessTokenEncoded);
}
