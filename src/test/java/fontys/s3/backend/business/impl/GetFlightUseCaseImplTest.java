package fontys.s3.backend.business.impl;

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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetFlightUseCaseImplTest {

    @Mock
    FlightRepository flightRepository;
    @InjectMocks
    private GetFlightUseCaseImpl useCase;
    private FlightEntity flightEntity;

    @Before
    public void setUp() {
        flightRepository = Mockito.mock(FlightRepository.class);
        useCase = new GetFlightUseCaseImpl(flightRepository);
    }
    @Test
    void getFlight() {
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
        when(flightRepository.findById(any())).thenReturn(Optional.of(flightEntity));
        //Act
        String flightNumber = "FR123";
        //Assert
        Assertions.assertEquals(flightEntity.getFlightNumber(), useCase.getFlight(flightNumber).get().getFlightNumber());
    }
}