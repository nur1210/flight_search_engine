package fontys.s3.backend.business.scheduling;

import fontys.s3.backend.business.UpdatePriceAlertUseCase;
import fontys.s3.backend.business.exception.InvalidFlightException;
import fontys.s3.backend.persistence.PriceAlertRepository;
import fontys.s3.backend.persistence.entity.FlightEntity;
import fontys.s3.backend.persistence.entity.PriceAlertEntity;
import fontys.s3.backend.persistence.tequilaapi.TequilaFlightsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduledTasksTest {
    @Mock
    private PriceAlertRepository priceAlertRepository;
    @Mock
    private TequilaFlightsRepository flightInfoRepository;
    @Mock
    private UpdatePriceAlertUseCase updatePriceAlertUseCase;
    @Mock
    private static PriceAlertEntity priceAlert;

    @InjectMocks
    private ScheduledTasks scheduledTasks;

    @BeforeEach
    void setUp() {
        priceAlert = PriceAlertEntity.builder()
                .id(1)
                .flyFrom("AMS")
                .flyTo("BCN")
                .dateFrom(new Date())
                .dateTo(new Date())
                .flightType("oneway")
                .passengers(1)
                .cabinClass("economy")
                .currency("EUR")
                .locale("en")
                .lowestPrice(100)
                .currentFlight(FlightEntity.builder().id(1).price(100).build())
                .build();
    }


    @Test
    void updatePriceAlertWhenFlightPriceHasNotChanged() {

        when(priceAlertRepository.findAll()).thenReturn(List.of(priceAlert));

        scheduledTasks.checkForChangeInFlightPrice();

        verify(updatePriceAlertUseCase, never()).updatePriceAlert(any());
    }

    @Test
    void deletePriceAlertWhenFlightDateHasExpired() {
        PriceAlertEntity priceAlertEntity =
                PriceAlertEntity.builder()
                        .dateFrom(new Date(System.currentTimeMillis() - 100000))
                        .build();

        when(priceAlertRepository.findAll()).thenReturn(List.of(priceAlertEntity));

        scheduledTasks.checkForChangeInFlightPrice();

        verify(priceAlertRepository, times(1)).delete(any());
    }

    @Test
    void getCheapestFlightWhenThereIsNoFlightAvailableThenThrowException() {

        when(flightInfoRepository.getFlightsInfo(any())).thenReturn(Collections.emptyList());

        assertThrows(
                InvalidFlightException.class, () -> scheduledTasks.getCheapestFlight(priceAlert));
    }

    @Test
    void getCheapestFlightWhenThereIsAFlightAvailableThenReturnTheCheapestFlight() {

        FlightEntity flight = FlightEntity.builder().id(1).availableSeats(2).price(100).build();

        when(flightInfoRepository.getFlightsInfo(any()))
                .thenReturn(List.of(flight));

        FlightEntity cheapestFlight =
                ReflectionTestUtils.invokeMethod(
                        scheduledTasks, "getCheapestFlight", priceAlert);

        assertEquals(flight, cheapestFlight);
    }

    @Test
    void checkForChangeInFlightPriceWhenDateFromIsBeforeTodayThenDeleteThePriceAlert() {
        PriceAlertEntity priceAlertEntity =
                PriceAlertEntity.builder()
                        .dateFrom(new Date(System.currentTimeMillis() - 100000))
                        .build();

        when(priceAlertRepository.findAll()).thenReturn(List.of(priceAlertEntity));

        scheduledTasks.checkForChangeInFlightPrice();

        verify(priceAlertRepository, times(1)).delete(priceAlertEntity);
    }

    @Test
    void checkForChangeInFlightPriceWhenCurrentFlightIsNullThenUpdateThePriceAlert() {

        FlightEntity cheapestFlight = FlightEntity.builder().id(1).price(100).build();

        when(priceAlertRepository.findAll()).thenReturn(List.of(priceAlert));
        when(flightInfoRepository.getFlightsInfo(any())).thenReturn(List.of(cheapestFlight));

        scheduledTasks.checkForChangeInFlightPrice();

        verify(updatePriceAlertUseCase, times(1)).updatePriceAlert(any());
    }

    @Test
    void
    checkForChangeInFlightPriceWhenCheapestFlightHasADifferentPriceThanCurrentFlightThenUpdateThePriceAlert() {

        PriceAlertEntity priceAlertEntity =
                PriceAlertEntity.builder()
                        .dateFrom(new Date(System.currentTimeMillis() + 100000))
                        .build();

        FlightEntity cheapestFlight = FlightEntity.builder().id(2).price(200).build();

        when(priceAlertRepository.findAll()).thenReturn(List.of(priceAlertEntity));

        when(flightInfoRepository.getFlightsInfo(any())).thenReturn(List.of(cheapestFlight));

        scheduledTasks.checkForChangeInFlightPrice();

        verify(updatePriceAlertUseCase, times(1)).updatePriceAlert(any());
    }
}