package fontys.s3.backend.business.impl;

import fontys.s3.backend.domain.CreateFlightRequest;
import fontys.s3.backend.domain.CreateFlightResponse;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateFlightUseCaseImplTest {
    @Mock
    FlightRepository flightRepository;
    @InjectMocks
    private CreateFlightUseCaseImpl useCase;
    private FlightEntity flightEntity;

    @Before
    public void setUp() {
        flightRepository = Mockito.mock(FlightRepository.class);
        useCase = new CreateFlightUseCaseImpl(flightRepository);
    }

    @Test
    void createFlight() {
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
                .economicPrice(100)
                .businessPrice(200)
                .build();
        when(flightRepository.save(any())).thenReturn(flightEntity);

        //Act
        CreateFlightRequest request = CreateFlightRequest.builder()
                .flightNumber(flightEntity.getFlightNumber())
                .airline(flightEntity.getAirline())
                .departureAirport(flightEntity.getDepartureAirport())
                .arrivalAirport(flightEntity.getArrivalAirport())
                .localDepartureTime(flightEntity.getLocalDepartureTime())
                .utcDepartureTime(flightEntity.getUtcDepartureTime())
                .localArrivalTime(flightEntity.getLocalArrivalTime())
                .utcArrivalTime(flightEntity.getUtcArrivalTime())
                .economicPrice(flightEntity.getEconomicPrice())
                .businessPrice(flightEntity.getBusinessPrice())
                .build();


        CreateFlightResponse response = useCase.CreateFlight(request);

        //Assert
        Assertions.assertEquals(response.getFlightNumber(), flightEntity.getFlightNumber());
    }
}