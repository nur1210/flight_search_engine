package fontys.s3.backend.business.scheduling;

import fontys.s3.backend.business.exception.InvalidFlightException;
import fontys.s3.backend.business.usecase.pricealert.UpdatePriceAlertUseCase;
import fontys.s3.backend.business.util.ObjectToUri;
import fontys.s3.backend.configuration.websocket.NotificationService;
import fontys.s3.backend.domain.model.FlightParams;
import fontys.s3.backend.domain.request.GetAllFlightsFromOriginToDestinationRequest;
import fontys.s3.backend.domain.request.UpdatePriceAlertRequest;
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
    private final PriceAlertRepository priceAlertRepository;
    private final TequilaFlightsRepository flightInfoRepository;
    private final UpdatePriceAlertUseCase updatePriceAlertUseCase;
    private final NotificationService notificationService;

    @Autowired
    public ScheduledTasks(PriceAlertRepository priceAlertRepository, TequilaFlightsRepository flightInfoRepository, UpdatePriceAlertUseCase updatePriceAlertUseCase, NotificationService notificationService) {
        this.priceAlertRepository = priceAlertRepository;
        this.flightInfoRepository = flightInfoRepository;
        this.updatePriceAlertUseCase = updatePriceAlertUseCase;
        this.notificationService = notificationService;
    }

    @Scheduled(fixedRate = 3600)
    public void checkForChangeInFlightPrice() {
        List<PriceAlertEntity> priceAlerts = priceAlertRepository.findAll();

        for (PriceAlertEntity priceAlert : priceAlerts) {
            if (priceAlert.getDateFrom().before(new Date())) {
                priceAlertRepository.delete(priceAlert);
                return;
            }


            var cheapestFlight = getCheapestFlight(priceAlert);

            if (priceAlert.getCurrentFlight() == null) {
                updatePriceAlert(priceAlert, cheapestFlight);
            } else if (cheapestFlight.getPrice() != priceAlert.getCurrentFlight().getPrice()) {
                updatePriceAlert(priceAlert, cheapestFlight);
/*                var param = ToStringUtil.toStringWithAttributes(priceAlert, ToStringStyle.NO_CLASS_NAME_STYLE).replace(",", "&");
                param = param.substring(1, param.length() - 1);*/
                for (var user : priceAlert.getUsers()) {
                    notificationService.sendPrivateNotification(user.getEmail(),
                            "The price for flight " + priceAlert.getFlyFrom() + " - " + priceAlert.getFlyTo() + " has changed!",
                            "Price changed", ObjectToUri.convert(convertPriceAlertToFlightParams(priceAlert)));
                }
            }
        }
    }

    FlightEntity getCheapestFlight(PriceAlertEntity priceAlert) {
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

    void updatePriceAlert(PriceAlertEntity priceAlert, FlightEntity cheapestFlight) {
        UpdatePriceAlertRequest request = UpdatePriceAlertRequest.builder()
                .priceAlertId(priceAlert.getId())
                .flight(cheapestFlight)
                .build();

        updatePriceAlertUseCase.updatePriceAlert(request);
    }

    private FlightParams convertPriceAlertToFlightParams(PriceAlertEntity priceAlert) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        return FlightParams.builder()
                .flyFrom(priceAlert.getFlyFrom())
                .flyTo(priceAlert.getFlyTo())
                .dateFrom(dateFormat.format(priceAlert.getDateFrom()))
                .dateTo(dateFormat.format(priceAlert.getDateFrom()))
                .returnFrom(priceAlert.getDateTo() == null ? null : dateFormat.format(priceAlert.getDateTo()))
                .returnTo(priceAlert.getDateTo() == null ? null : dateFormat.format(priceAlert.getDateTo()))
                .flightType(priceAlert.getFlightType())
                .adults(String.valueOf(priceAlert.getPassengers()))
                .selectedCabins(priceAlert.getCabinClass())
                .currency(priceAlert.getCurrency())
                .language(priceAlert.getLocale())
                .maxStopovers("0")
                .maxSectorStopovers("0")
                .build();
    }
}
