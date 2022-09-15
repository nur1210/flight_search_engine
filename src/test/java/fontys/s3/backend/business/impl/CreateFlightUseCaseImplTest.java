package fontys.s3.backend.business.impl;

import fontys.s3.backend.business.AirportValidator;
import fontys.s3.backend.domain.CreateFlightRequest;
import fontys.s3.backend.domain.CreateFlightResponse;
import fontys.s3.backend.persistence.AirportRepository;
import fontys.s3.backend.persistence.FlightRepository;
import fontys.s3.backend.persistence.entity.AddressEntity;
import fontys.s3.backend.persistence.entity.AirportEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Timestamp;
@RunWith(MockitoJUnitRunner.class)
class CreateFlightUseCaseImplTest {

    @Mock
    AirportRepository airportRepository;
    @Mock
    FlightRepository flightRepository;

    @Test
    void createFlight() {
        //Arrange
        AirportValidator airportValidator = new AirportValidatorImpl(airportRepository);
        CreateFlightUseCaseImpl useCase = new CreateFlightUseCaseImpl(flightRepository, airportRepository, airportValidator);
        airportRepository.save(
                AirportEntity.builder()
                        .airportCode("AMS")
                        .name("Amsterdam Airport Schiphol")
                        .address(AddressEntity.builder()
                                .id(1L)
                                .street("street")
                                .city("city")
                                .country("country")
                                .build())
                        .build()
        );

        //Act
        CreateFlightRequest request = CreateFlightRequest.builder()
                .flightNumber("FR123")
                .airline("KLM")
                .originCode("AMS")
                .destinationCode("LHR")
                .flightTime(Timestamp.valueOf("2021-01-01 00:00:00"))
                .landTime(Timestamp.valueOf("2021-01-01 00:00:00"))
                .economicPrice(100)
                .businessPrice(200)
                .build();

        CreateFlightResponse response = useCase.CreateFlight(request);

        //Assert
        Assertions.assertEquals("FR123", response.getFlightNumber());
    }
}