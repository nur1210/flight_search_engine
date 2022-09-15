package fontys.s3.backend.business.impl;

import fontys.s3.backend.domain.CreateAirportRequest;
import fontys.s3.backend.domain.CreateAirportResponse;
import fontys.s3.backend.persistence.AddressRepository;
import fontys.s3.backend.persistence.AirportRepository;
import fontys.s3.backend.persistence.entity.AddressEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class CreateAirportUseCaseImplTest {

    @Mock
    AirportRepository airportRepository;
    @Mock
    AddressRepository addressRepository;

    @BeforeEach
    private void init() {
        AddressEntity address = AddressEntity.builder()
                .id(1L)
                .street("street")
                .city("city")
                .country("country")
                .build();
        addressRepository.save(address);
    }

    @Test
    void createAirport() {
        //Arrange
        CreateAirportUseCaseImpl useCase = new CreateAirportUseCaseImpl(airportRepository, addressRepository);
        AddressEntity address = AddressEntity.builder()
                .id(1L)
                .street("street")
                .city("city")
                .country("country")
                .build();
        //Act
        CreateAirportRequest request = CreateAirportRequest.builder()
                .airportCode("AMS")
                .name("Amsterdam Airport Schiphol")
                .address(address)
                .build();

        CreateAirportResponse response = useCase.createAirport(request);

        //Assert
        Assertions.assertEquals("AMS", response.getAirportCode());
    }
}