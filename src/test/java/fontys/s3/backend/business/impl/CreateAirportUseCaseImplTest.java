package fontys.s3.backend.business.impl;

import fontys.s3.backend.domain.CreateAirportRequest;
import fontys.s3.backend.domain.CreateAirportResponse;
import fontys.s3.backend.persistence.AddressRepository;
import fontys.s3.backend.persistence.AirportRepository;
import fontys.s3.backend.persistence.entity.AddressEntity;
import fontys.s3.backend.persistence.entity.AirportEntity;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateAirportUseCaseImplTest {

    @Mock
    AirportRepository airportRepository;
    @Mock
    AddressRepository addressRepository;
    @InjectMocks
    CreateAirportUseCaseImpl useCase;

    @Before
    public void setUp() {
        airportRepository = Mockito.mock(AirportRepository.class);
        addressRepository = Mockito.mock(AddressRepository.class);
        useCase = new CreateAirportUseCaseImpl(airportRepository, addressRepository);
    }

    @Test
    void createAirport() {
        //Arrange
        AddressEntity address = AddressEntity.builder()
                .id(1L)
                .street("street")
                .city("city")
                .country("country")
                .build();
        AirportEntity airport = AirportEntity.builder()
                .airportCode("AMS")
                .name("Amsterdam Airport Schiphol")
                .address(address)
                .build();
        when(airportRepository.save(any())).thenReturn(airport);

        //Act
        CreateAirportRequest request = CreateAirportRequest.builder()
                .airportCode("AMS")
                .name("Amsterdam Airport Schiphol")
                .address(address)
                .build();

        CreateAirportResponse response = useCase.createAirport(request);

        //Assert
        Assertions.assertEquals("AMS", response.getAirportCode());
        Assertions.assertEquals(airport.getAirportCode(), response.getAirportCode());
    }
}