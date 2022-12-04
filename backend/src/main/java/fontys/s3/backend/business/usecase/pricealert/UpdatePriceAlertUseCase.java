package fontys.s3.backend.business.usecase.pricealert;

import fontys.s3.backend.domain.request.UpdatePriceAlertRequest;

public interface UpdatePriceAlertUseCase {
    void updatePriceAlert(UpdatePriceAlertRequest request);
}
