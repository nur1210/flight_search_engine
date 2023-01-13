package fontys.s3.backend.business.impl.flight;

import fontys.s3.backend.domain.request.GetAdvanceSearchFlightsRequest;
import fontys.s3.backend.domain.response.GetFlightsResponse;
import fontys.s3.backend.persistence.entity.FlightEntity;
import fontys.s3.backend.persistence.tequilaapi.TequilaFlightsRepository;
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
class GetAdvanceSearchFlightsUseCaseImplTest {

    @Mock
    private TequilaFlightsRepository flightInfoRepository;

    @InjectMocks
    private GetAdvanceSearchFlightsUseCaseImpl getAdvanceSearchFlightsUseCase;

    @Test
    void getAdvanceSearchFlights_ShouldReturnFlights() {
        // Arrange
        FlightEntity flightEntity = FlightEntity.builder()
                .id(1L)
                .price(200)
                .outboundRoutes(Collections.emptyList())
                .returnRoutes(Collections.emptyList())
                .availableSeats(4)
                .deepLink("Hello")
                .build();
        List<FlightEntity> flights = Collections.singletonList(flightEntity);
        when(flightInfoRepository.getFlightsInfo(any())).thenReturn(flights);
        GetAdvanceSearchFlightsRequest request = new GetAdvanceSearchFlightsRequest();

        // Act
        GetFlightsResponse response = getAdvanceSearchFlightsUseCase.getAdvanceSearchFlights(request);

        // Assert
        assertEquals(1, response.getFlights().size());
        assertEquals(1L, response.getFlights().get(0).getId());
        assertEquals(200, response.getFlights().get(0).getPrice());
    }

    @Test
    void getAdvanceSearchFlights_ShouldReturnEmptyList_WhenNoFlightsFound() {
        // Arrange
        List<FlightEntity> flights = Collections.emptyList();
        when(flightInfoRepository.getFlightsInfo(any())).thenReturn(flights);
        GetAdvanceSearchFlightsRequest request = new GetAdvanceSearchFlightsRequest();

        // Act
        GetFlightsResponse response = getAdvanceSearchFlightsUseCase.getAdvanceSearchFlights(request);

        // Assert
        assertEquals(0, response.getFlights().size());
    }
}
