package fontys.s3.backend.business;

import fontys.s3.backend.domain.UpdatePriceAlertRequest;

public interface UpdatePriceAlertUseCase {
    void updatePriceAlert(UpdatePriceAlertRequest request);
}
