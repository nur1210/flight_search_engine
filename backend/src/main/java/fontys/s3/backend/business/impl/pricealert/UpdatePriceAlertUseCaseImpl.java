package fontys.s3.backend.business.impl.pricealert;

import fontys.s3.backend.business.exception.InvalidPriceAlertException;
import fontys.s3.backend.business.usecase.pricealert.UpdatePriceAlertUseCase;
import fontys.s3.backend.domain.request.UpdatePriceAlertRequest;
import fontys.s3.backend.persistence.FlightRepository;
import fontys.s3.backend.persistence.PriceAlertRepository;
import fontys.s3.backend.persistence.entity.FlightEntity;
import fontys.s3.backend.persistence.entity.PriceAlertEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdatePriceAlertUseCaseImpl implements UpdatePriceAlertUseCase {

    private final PriceAlertRepository priceAlertRepository;
    private final FlightRepository flightRepository;

    @Override
    public void updatePriceAlert(UpdatePriceAlertRequest request) {
        Optional<PriceAlertEntity> priceAlertOptional = priceAlertRepository.findById(request.getPriceAlertId());
        if (priceAlertOptional.isEmpty()) {
            throw new InvalidPriceAlertException("PRICE_ALERT_ID_INVALID");
        }

        PriceAlertEntity priceAlert = priceAlertOptional.get();
        var prevFlight = priceAlert.getCurrentFlight();
        updateFields(request, priceAlert, prevFlight);
    }

    private void updateFields(UpdatePriceAlertRequest request, PriceAlertEntity priceAlert, FlightEntity prevFlight) {
        FlightEntity flight = request.getFlight();
        priceAlert.setCurrentFlight(flight);
        if (flight.getPrice() < priceAlert.getLowestPrice() || priceAlert.getLowestPrice() == 0) {
            priceAlert.setLowestPrice(flight.getPrice());
            priceAlert.setCurrentFlight(flight);
            if (prevFlight != null) flightRepository.delete(prevFlight);
        }

        priceAlertRepository.save(priceAlert);
    }
}
