package fontys.s3.backend.business.impl;

import fontys.s3.backend.domain.UpdateFlightRequest;
import fontys.s3.backend.persistence.FlightRepository;
import fontys.s3.backend.persistence.entity.AirportEntity;
import fontys.s3.backend.persistence.entity.FlightEntity;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;

@ExtendWith(MockitoExtension.class)
class UpdateFlightUseCaseImplTest {
    @Mock
    FlightRepository flightRepository;
    @InjectMocks
    private UpdateFlightUseCaseImpl useCase;
    private FlightEntity flightEntity;

    @Before
    public void setUp() {
        flightRepository = Mockito.mock(FlightRepository.class);
        useCase = new UpdateFlightUseCaseImpl(flightRepository);
    }
    @Test
    void updateFlight() {
        //Arrange
        AirportEntity airport = AirportEntity.builder()
                .iata("AMS")
                .city("Amsterdam")
                .cityCode("AMS")
                .country("Netherlands")
                .countryCode("NL")
                .build();

        flightEntity = FlightEntity.builder()
                .flightNumber("FR123")
                .airline("KLM")
                .departureAirport(airport)
                .arrivalAirport(airport)
                .localDepartureTime(Timestamp.valueOf("2021-01-01 00:00:00"))
                .utcDepartureTime(Timestamp.valueOf("2021-01-01 00:00:00"))
                .localArrivalTime(Timestamp.valueOf("2021-01-01 00:00:00"))
                .utcArrivalTime(Timestamp.valueOf("2021-01-01 00:00:00"))
                .price(100)
                .build();

        //Act
        UpdateFlightRequest request = UpdateFlightRequest.builder()
                .flightNumber("FR23")
                .localDepartureTime(Timestamp.valueOf("2021-01-01 00:00:00"))
                .utcDepartureTime(Timestamp.valueOf("2021-01-01 00:00:00"))
                .localArrivalTime(Timestamp.valueOf("2021-01-01 03:00:00"))
                .utcArrivalTime(Timestamp.valueOf("2021-01-01 00:00:00"))
                .economicPrice(100)
                .businessPrice(200)
                .build();

        //Assert
        Assertions.assertNotEquals(request.getFlightNumber(), flightEntity.getFlightNumber());
        Assertions.assertNotEquals(request.getLocalArrivalTime(), flightEntity.getLocalArrivalTime());

    }
}