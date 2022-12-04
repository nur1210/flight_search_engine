package fontys.s3.backend.business.usecase.token;

public interface RefreshTokenUseCase {
    String refreshAccessToken(String refreshToken);
}
