package fontys.s3.backend.business.impl;

import fontys.s3.backend.business.impl.airport.CreateAirportUseCaseImpl;
import fontys.s3.backend.domain.request.CreateAirportRequest;
import fontys.s3.backend.domain.response.CreateAirportResponse;
import fontys.s3.backend.persistence.AirportRepository;
import fontys.s3.backend.persistence.entity.AirportEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {CreateAirportUseCaseImpl.class})
@ExtendWith(MockitoExtension.class)
class CreateAirportUseCaseImplTest {

    @Mock
    AirportRepository airportRepository;
    @InjectMocks
    CreateAirportUseCaseImpl useCase;


    @BeforeEach
    public void setUp() {
        airportRepository = Mockito.mock(AirportRepository.class);
        useCase = new CreateAirportUseCaseImpl(airportRepository);
    }

    @Test
    void createAirport() {
        //Arrange
        AirportEntity airport = AirportEntity.builder()
                .iata("AMS")
                .city("Amsterdam")
                .cityCode("AMS")
                .country("Netherlands")
                .countryCode("NL")
                .build();
        when(airportRepository.save(any())).thenReturn(airport);

        //Act
        CreateAirportRequest request = CreateAirportRequest.builder()
                .iata("AMS")
                .city("Amsterdam")
                .cityCode("AMS")
                .country("Netherlands")
                .countryCode("NL")
                .build();

        CreateAirportResponse response = useCase.createAirport(request);

        //Assert
        Assertions.assertEquals("AMS", response.getIata());
        Assertions.assertEquals(airport.getIata(), response.getIata());
    }
}