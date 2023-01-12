package fontys.s3.backend.business.impl.flight;

import fontys.s3.backend.domain.request.GetTopThreeCheapestFlightsFromUserLocationRequest;
import fontys.s3.backend.domain.response.GetFlightsResponse;
import fontys.s3.backend.persistence.entity.FlightEntity;
import fontys.s3.backend.persistence.tequilaapi.TequilaFlightsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetTopThreeCheapestFlightsFromUserLocationUseCaseImplTest {
    @Mock
    private TequilaFlightsRepository flightInfoRepository;
    @InjectMocks
    private GetTopThreeCheapestFlightsFromUserLocationUseCaseImpl getTopThreeCheapestFlightsUseCase;
    private List<FlightEntity> flights;

    @BeforeEach
    void setUp() {
        flights = List.of(
                FlightEntity.builder()
                        .id(1L)
                        .outboundRoutes(Collections.emptyList())
                        .returnRoutes(Collections.emptyList())
                        .availableSeats(4)
                        .deepLink("link")
                        .price(200)
                        .build(),
                FlightEntity.builder()
                        .id(2L)
                        .price(300)
                        .outboundRoutes(Collections.emptyList())
                        .returnRoutes(Collections.emptyList())
                        .availableSeats(4)
                        .deepLink("link")
                        .build(),
                FlightEntity.builder()
                        .id(3L)
                        .outboundRoutes(Collections.emptyList())
                        .returnRoutes(Collections.emptyList())
                        .availableSeats(4)
                        .deepLink("link")
                        .price(100)
                        .build());
    }

    @Test
    void getTopThreeCheapestFlightsFromUserLocation_ShouldReturnTopThreeCheapestFlights() {
        // Arrange
        when(flightInfoRepository.getFlightsInfo(any())).thenReturn(flights);
        GetTopThreeCheapestFlightsFromUserLocationRequest request = GetTopThreeCheapestFlightsFromUserLocationRequest.builder()
                .flyFrom("AMS")
                .dateFrom("2022-01-01")
                .dateTo("2022-01-31")
                .build();
        // Act
        final GetFlightsResponse response = getTopThreeCheapestFlightsUseCase.getTopThreeCheapestFlightsFromUserLocation(request);

        // Assert
        assertEquals(3, response.getFlights().size());
        assertEquals(200, response.getFlights().get(0).getPrice());
        assertEquals(300, response.getFlights().get(1).getPrice());
        assertEquals(100, response.getFlights().get(2).getPrice());
    }
}