package fontys.s3.backend.business.impl;

import fontys.s3.backend.domain.UpdateFlightRequest;
import fontys.s3.backend.persistence.FlightRepository;
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
        flightEntity = FlightEntity.builder()
                .flightNumber("FR123")
                .airline("KLM")
                .originCode("AMS")
                .destinationCode("LHR")
                .flightTime(Timestamp.valueOf("2021-01-01 00:00:00"))
                .landTime(Timestamp.valueOf("2021-01-01 00:00:00"))
                .economicPrice(100)
                .businessPrice(200)
                .build();

        //Act
        UpdateFlightRequest request = UpdateFlightRequest.builder()
                .flightNumber("FR23")
                .flightTime(Timestamp.valueOf("2022-01-01 00:00:00"))
                .landTime(Timestamp.valueOf("2022-01-01 00:00:00"))
                .economicPrice(20)
                .businessPrice(500)
                .build();

        //Assert
        Assertions.assertNotEquals(request.getFlightNumber(), flightEntity.getFlightNumber());
        Assertions.assertNotEquals(request.getLandTime(), flightEntity.getLandTime());

    }
}