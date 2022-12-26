package fontys.s3.backend.business.impl;

import fontys.s3.backend.business.impl.flight.GetAllFlightsFromOriginToDestinationUseCaseImpl;
import fontys.s3.backend.domain.model.Flight;
import fontys.s3.backend.domain.model.FlightParams;
import fontys.s3.backend.domain.request.GetAllFlightsFromOriginToDestinationRequest;
import fontys.s3.backend.domain.response.GetFlightsResponse;
import fontys.s3.backend.persistence.entity.AirportEntity;
import fontys.s3.backend.persistence.entity.FlightEntity;
import fontys.s3.backend.persistence.entity.RouteEntity;
import fontys.s3.backend.persistence.tequilaapi.TequilaFlightsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllFlightsFromOriginToDestinationUseCaseImplTest {

    @InjectMocks
    private GetAllFlightsFromOriginToDestinationUseCaseImpl getAllFlightsFromOriginToDestinationUseCase;

    @Mock
    private TequilaFlightsRepository tequilaFlightsRepository;

    @Mock
    private FlightParams flightParams;

    @Test
    void testGetAllFlightsFromOriginToDestination_ReturnsExpectedResults() {
        // Setup
        GetAllFlightsFromOriginToDestinationRequest request = createTestRequest();
        List<FlightEntity> expectedFlightEntities = createTestFlightEntities();
        when(tequilaFlightsRepository.getFlightsInfo(any())).thenReturn(expectedFlightEntities);

        // Execute
        GetFlightsResponse response = getAllFlightsFromOriginToDestinationUseCase.getAllFlightsFromOriginToDestination(request);

        // Verify
        List<Flight> flights = response.getFlights();
        assertEquals(expectedFlightEntities.size(), flights.size());
        for (int i = 0; i < expectedFlightEntities.size(); i++) {
            FlightEntity expectedEntity = expectedFlightEntities.get(i);
            Flight actualFlight = flights.get(i);
            assertEquals(expectedEntity.getPrice(), actualFlight.getPrice());
            assertEquals(expectedEntity.getAvailableSeats(), actualFlight.getAvailableSeats());
            // Add additional assertions to verify the rest of the Flight fields
        }
    }

    @Test
    void testGetAllFlightsFromOriginToDestination_ReturnsEmptyResults() {
        // Setup
        GetAllFlightsFromOriginToDestinationRequest request = createTestRequest();
        when(tequilaFlightsRepository.getFlightsInfo(any())).thenReturn(Collections.emptyList());

        // Execute
        GetFlightsResponse response = getAllFlightsFromOriginToDestinationUseCase.getAllFlightsFromOriginToDestination(request);

        // Verify
        List<Flight> flights = response.getFlights();
        assertTrue(flights.isEmpty());
    }

    @Test
    void testGetAllFlightsFromOriginToDestination_HandlesRepositoryException() {

        GetAllFlightsFromOriginToDestinationRequest request = createTestRequest();
        request.setReturnFrom("2000-01-01");
        when(tequilaFlightsRepository.getFlightsInfo(any())).thenThrow(new IllegalArgumentException("RETURN_DATE_IS_BEFORE_DEPARTURE_DATE"));

        assertThrows(
                IllegalArgumentException.class, () -> getAllFlightsFromOriginToDestinationUseCase.getAllFlightsFromOriginToDestination(request));
    }


    private GetAllFlightsFromOriginToDestinationRequest createTestRequest() {
        // Create and return a test GetAllFlightsFromOriginToDestinationRequest object
        return GetAllFlightsFromOriginToDestinationRequest.builder()
                .flyFrom("AMS")
                .flyTo("LHR")
                .dateFrom("2021-01-01")
                .dateTo("2021-01-02")
                .returnFrom("2021-01-03")
                .returnTo("2021-01-04")
                .flightType("round")
                .adults(1)
                .selectedCabins("M")
                .currency("EUR")
                .language("en")
                .maxStopovers(0)
                .maxSectorStopovers(0)
                .limit(10)
                .build();
    }

    private List<FlightEntity> createTestFlightEntities() {
        // Create and return a list of test FlightEntity objects
        RouteEntity route = RouteEntity.builder()
                .flightNumber(123)
                .departureAirport(mock(AirportEntity.class))
                .arrivalAirport(mock(AirportEntity.class))
                .localDepartureTime(new Timestamp(System.currentTimeMillis() + 3 * 24 * 60 * 60 * 1000))
                .localArrivalTime(new Timestamp(System.currentTimeMillis() + 4 * 24 * 60 * 60 * 1000))
                .utcDepartureTime(new Timestamp(System.currentTimeMillis() + 3 * 24 * 60 * 60 * 1000))
                .utcArrivalTime(new Timestamp(System.currentTimeMillis() + 4 * 24 * 60 * 60 * 1000))
                .airline("KLM")
                .build();

        return Collections.singletonList(FlightEntity.builder()
                .id(1L)
                .outboundRoutes(Collections.singletonList(route))
                .returnRoutes(Collections.singletonList(route))
                .deepLink("https://www.google.com")
                .price(100)
                .availableSeats(10)
                .build());
    }
}