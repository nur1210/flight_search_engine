package fontys.s3.backend.business.impl;

import fontys.s3.backend.domain.CreateFlightRequest;
import fontys.s3.backend.domain.CreateFlightResponse;
import fontys.s3.backend.domain.JSONCountryFrom;
import fontys.s3.backend.domain.JSONCountryTo;
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
                .airline("FR")
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
                .flight_no(123)
                .airline(flightEntity.getAirline())
                .flyFrom(flightEntity.getDepartureAirport().getIata())
                .cityFrom(flightEntity.getDepartureAirport().getCity())
                .cityCodeFrom(flightEntity.getDepartureAirport().getCityCode())
                .countryFrom(JSONCountryFrom.builder()
                        .code(flightEntity.getDepartureAirport().getCountryCode())
                        .name(flightEntity.getDepartureAirport().getCountry())
                        .build())
                .flyTo(flightEntity.getArrivalAirport().getIata())
                .cityTo(flightEntity.getArrivalAirport().getCity())
                .cityCodeTo(flightEntity.getArrivalAirport().getCityCode())
                .countryTo(JSONCountryTo.builder()
                        .code(flightEntity.getArrivalAirport().getCountryCode())
                        .name(flightEntity.getArrivalAirport().getCountry())
                        .build())
                .local_departure(flightEntity.getLocalDepartureTime())
                .utc_departure(flightEntity.getUtcDepartureTime())
                .local_arrival(flightEntity.getLocalArrivalTime())
                .utc_arrival(flightEntity.getUtcArrivalTime())
                .economicPrice(flightEntity.getEconomicPrice())
                .businessPrice(flightEntity.getBusinessPrice())
                .build();


        CreateFlightResponse response = useCase.CreateFlight(request);

        //Assert
        Assertions.assertEquals(response.getFlightNumber(), flightEntity.getFlightNumber());
    }
}