package fontys.s3.backend.business;

import fontys.s3.backend.domain.CreatePriceAlertRequest;
import fontys.s3.backend.domain.CreatePriceAlertResponse;

public interface CreatePriceAlertUseCase {
    CreatePriceAlertResponse createPriceAlert(CreatePriceAlertRequest request);
}
