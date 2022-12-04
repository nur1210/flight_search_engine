package fontys.s3.backend.business.usecase.pricealert;

import fontys.s3.backend.domain.request.CreatePriceAlertRequest;
import fontys.s3.backend.domain.response.CreatePriceAlertResponse;

public interface CreatePriceAlertUseCase {
    CreatePriceAlertResponse createPriceAlert(CreatePriceAlertRequest request);
}
