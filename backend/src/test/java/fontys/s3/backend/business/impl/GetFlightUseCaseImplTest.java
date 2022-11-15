package fontys.s3.backend.business.impl;

import fontys.s3.backend.persistence.FlightRepository;
import fontys.s3.backend.persistence.entity.AirportEntity;
import fontys.s3.backend.persistence.entity.FlightEntity;
import fontys.s3.backend.persistence.entity.RouteEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetFlightUseCaseImplTest {

    @Mock
    FlightRepository flightRepository;
    @InjectMocks
    private GetFlightUseCaseImpl useCase;

    @BeforeEach
    public void setUp() {
        flightRepository = Mockito.mock(FlightRepository.class);
        useCase = new GetFlightUseCaseImpl(flightRepository);
    }
    @Test
    void getFlight() {
        //Arrange
        AirportEntity airport = AirportEntity.builder()
                .iata("AMS")
                .city("Amsterdam")
                .cityCode("AMS")
                .country("Netherlands")
                .countryCode("NL")
                .build();

        RouteEntity route = RouteEntity.builder()
                .flightNumber(1234)
                .airline("FR")
                .departureAirport(airport)
                .arrivalAirport(airport)
                .localDepartureTime(Timestamp.valueOf("2021-01-01 00:00:00"))
                .utcDepartureTime(Timestamp.valueOf("2021-01-01 00:00:00"))
                .localArrivalTime(Timestamp.valueOf("2021-01-01 00:00:00"))
                .utcArrivalTime(Timestamp.valueOf("2021-01-01 00:00:00"))
                .build();
        List<RouteEntity> routes = new ArrayList<>();
        routes.add(route);

        FlightEntity flightEntity = FlightEntity.builder()
                .route(routes)
                .price(100)
                .availableSeats(2)
                .build();

        when(flightRepository.findById(any())).thenReturn(Optional.of(flightEntity));
        //Act
        long flightId = 1;
        //Assert
        //Assertions.assertEquals(flightEntity.getId(), useCase.getFlight(flightId));
    }
}