package fontys.s3.backend.business.impl;

import fontys.s3.backend.business.exception.InvalidPriceAlertException;
import fontys.s3.backend.business.impl.pricealert.UpdatePriceAlertUseCaseImpl;
import fontys.s3.backend.domain.request.UpdatePriceAlertRequest;
import fontys.s3.backend.persistence.FlightRepository;
import fontys.s3.backend.persistence.PriceAlertRepository;
import fontys.s3.backend.persistence.entity.FlightEntity;
import fontys.s3.backend.persistence.entity.PriceAlertEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdatePriceAlertUseCaseImplTest {

    @Mock
    private PriceAlertRepository priceAlertRepository;
    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private UpdatePriceAlertUseCaseImpl updatePriceAlertUseCase;

    @Test
    void updatePriceAlertWhenPriceAlertIdIsInvalidThenThrowException() {
        UpdatePriceAlertRequest request =
                UpdatePriceAlertRequest.builder().priceAlertId(1L).build();

        when(priceAlertRepository.findById(request.getPriceAlertId())).thenReturn(Optional.empty());

        assertThrows(
                InvalidPriceAlertException.class,
                () -> updatePriceAlertUseCase.updatePriceAlert(request));
    }

    @Test
    @DisplayName(
            "Should update the current flight and lowest price when the price alert id is valid")
    void updatePriceAlertWhenPriceAlertIdIsValidThenUpdateCurrentFlightAndLowestPrice() {
        UpdatePriceAlertRequest request =
                UpdatePriceAlertRequest.builder()
                        .priceAlertId(1)
                        .flight(FlightEntity.builder().id(1).price(50).build())
                        .build();

        PriceAlertEntity priceAlert =
                PriceAlertEntity.builder()
                        .id(1)
                        .lowestPrice(100)
                        .currentFlight(FlightEntity.builder().id(2).price(200).build())
                        .build();

        when(priceAlertRepository.findById(priceAlert.getId()))
                .thenReturn(Optional.of(priceAlert));
        doNothing().when(flightRepository).delete(any());

        updatePriceAlertUseCase.updatePriceAlert(request);

        assertEquals(request.getFlight().getId(), priceAlert.getCurrentFlight().getId());
        assertEquals(request.getFlight().getPrice(), priceAlert.getLowestPrice());

        verify(priceAlertRepository, times(1)).findById(request.getPriceAlertId());
        verify(priceAlertRepository, times(1)).save(priceAlert);
    }

    @Test
    void updatePriceAlertWhenPriceAlertIdIsValidDontUpdateCurrentFlightAndLowestPriceBecausePriceAlertIsLowerThenFlightPrice() {
        UpdatePriceAlertRequest request =
                UpdatePriceAlertRequest.builder()
                        .priceAlertId(1)
                        .flight(FlightEntity.builder().id(1).price(100).build())
                        .build();

        PriceAlertEntity priceAlert =
                PriceAlertEntity.builder()
                        .id(1)
                        .lowestPrice(47)
                        .currentFlight(FlightEntity.builder().id(2).price(74).build())
                        .build();

        when(priceAlertRepository.findById(priceAlert.getId()))
                .thenReturn(Optional.of(priceAlert));

        updatePriceAlertUseCase.updatePriceAlert(request);

        assertEquals(request.getFlight().getId(), priceAlert.getCurrentFlight().getId());
        assertNotEquals(request.getFlight().getPrice(), priceAlert.getLowestPrice());

        verify(priceAlertRepository, times(1)).findById(request.getPriceAlertId());
        verify(priceAlertRepository, times(1)).save(priceAlert);
    }
}