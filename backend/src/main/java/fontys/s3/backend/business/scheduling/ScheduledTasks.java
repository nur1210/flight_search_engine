package fontys.s3.backend.business.scheduling;

import fontys.s3.backend.business.UpdatePriceAlertUseCase;
import fontys.s3.backend.business.exception.InvalidFlightException;
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
import org.springframework.util.ReflectionUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class ScheduledTasks {
    @Autowired
    private PriceAlertRepository priceAlertRepository;
    @Autowired
    private TequilaFlightsRepository flightInfoRepository;
    @Autowired
    private UpdatePriceAlertUseCase updatePriceAlertUseCase;

    @Scheduled(fixedRate = 360000)
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
                .fly_from(priceAlert.getFlyFrom())
                .fly_to(priceAlert.getFlyTo())
                .date_from(dateFormat.format(priceAlert.getDateFrom()))
                .date_to(dateFormat.format(priceAlert.getDateFrom()))
                .return_from(priceAlert.getDateTo() == null ? null : dateFormat.format(priceAlert.getDateTo()))
                .return_to(priceAlert.getDateTo() == null ? null : dateFormat.format(priceAlert.getDateTo()))
                .flight_type(priceAlert.getFlightType())
                .adults(priceAlert.getPassengers())
                .selected_cabins(priceAlert.getCabinClass())
                .curr(priceAlert.getCurrency())
                .locale(priceAlert.getLocale())
                .max_stopovers(0)
                .max_sector_stopovers(0)
                .build();

        Map<String, Object> params = new HashMap<>();
        ReflectionUtils.doWithFields(request.getClass(), field -> {
            params.put(field.getName(), field.get(request));
            field.setAccessible(true);
        });

        FlightEntity cheapestFlight = flightInfoRepository.getFlightsInfo(params)
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
