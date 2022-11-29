package fontys.s3.backend.business.scheduling;

import fontys.s3.backend.business.UpdatePriceAlertUseCase;
import fontys.s3.backend.business.exception.InvalidFlightException;
import fontys.s3.backend.domain.FlightParams;
import fontys.s3.backend.domain.GetAllFlightsFromOriginToDestinationRequest;
import fontys.s3.backend.domain.UpdatePriceAlertRequest;
import fontys.s3.backend.persistence.PriceAlertRepository;
import fontys.s3.backend.persistence.entity.FlightEntity;
import fontys.s3.backend.persistence.entity.PriceAlertEntity;
import fontys.s3.backend.persistence.tequilaapi.TequilaFlightsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class ScheduledTasks {
    @Autowired
    private PriceAlertRepository priceAlertRepository;
    @Autowired
    private TequilaFlightsRepository flightInfoRepository;
    @Autowired
    private UpdatePriceAlertUseCase updatePriceAlertUseCase;

    @Scheduled(fixedRate = 36000)
    public void checkForChangeInFlightPrice() {
        List<PriceAlertEntity> priceAlerts = priceAlertRepository.findAll();

        for (PriceAlertEntity priceAlert : priceAlerts) {
            if (priceAlert.getDateFrom().before(new Date())) {
                priceAlertRepository.delete(priceAlert);
                return;
            }

            var cheapestFlight = getCheapestFlight(priceAlert);

            if (priceAlert.getCurrentFlight() ==  null) {
                updatePriceAlert(priceAlert, cheapestFlight);
            }
            else if (cheapestFlight.getPrice() != priceAlert.getCurrentFlight().getPrice()) {
                updatePriceAlert(priceAlert, cheapestFlight);
                //send email
            }
        }
    }

    private FlightEntity getCheapestFlight(PriceAlertEntity priceAlert) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        GetAllFlightsFromOriginToDestinationRequest request = GetAllFlightsFromOriginToDestinationRequest.builder()
                .flyFrom(priceAlert.getFlyFrom())
                .flyTo(priceAlert.getFlyTo())
                .dateFrom(dateFormat.format(priceAlert.getDateFrom()))
                .dateTo(dateFormat.format(priceAlert.getDateFrom()))
                .returnFrom(priceAlert.getDateTo() == null ? null : dateFormat.format(priceAlert.getDateTo()))
                .returnTo(priceAlert.getDateTo() == null ? null : dateFormat.format(priceAlert.getDateTo()))
                .flightType(priceAlert.getFlightType())
                .adults(priceAlert.getPassengers())
                .selectedCabins(priceAlert.getCabinClass())
                .currency(priceAlert.getCurrency())
                .language(priceAlert.getLocale())
                .maxStopovers(0)
                .maxSectorStopovers(0)
                .build();


        FlightParams flightParams = FlightParams.builder()
                .flyFrom(request.getFlyFrom())
                .flyTo(request.getFlyTo())
                .dateFrom(request.getDateFrom())
                .dateTo(request.getDateTo())
                .returnFrom(request.getReturnFrom())
                .returnTo(request.getReturnTo())
                .flightType(request.getFlightType())
                .adults(String.valueOf(request.getAdults()))
                .selectedCabins(request.getSelectedCabins())
                .currency(request.getCurrency())
                .language(request.getLanguage())
                .maxStopovers(String.valueOf(request.getMaxStopovers()))
                .maxSectorStopovers(String.valueOf(request.getMaxSectorStopovers()))
                .build();

        FlightEntity cheapestFlight = flightInfoRepository.getFlightsInfo(flightParams)
                .stream().filter(f ->
                        f.getAvailableSeats() >= request.adults)
                .findFirst().orElse(null);

        if (cheapestFlight == null) {
            throw new InvalidFlightException("NO_FLIGHTS_FOUND");
        }

        return cheapestFlight;
    }

    private void updatePriceAlert(PriceAlertEntity priceAlert, FlightEntity cheapestFlight) {
        UpdatePriceAlertRequest request = UpdatePriceAlertRequest.builder()
                .priceAlertId(priceAlert.getId())
                .flight(cheapestFlight)
                .build();

        updatePriceAlertUseCase.updatePriceAlert(request);
    }
}
