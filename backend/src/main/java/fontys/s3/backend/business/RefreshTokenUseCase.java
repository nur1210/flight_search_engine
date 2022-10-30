package fontys.s3.backend.business;

public interface RefreshTokenUseCase {
    String refreshAccessToken(String refreshToken);
}
