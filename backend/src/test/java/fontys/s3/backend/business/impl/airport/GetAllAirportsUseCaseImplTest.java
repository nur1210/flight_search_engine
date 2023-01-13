package fontys.s3.backend.business.impl.airport;

import fontys.s3.backend.domain.response.GetAllAirportsResponse;
import fontys.s3.backend.persistence.AirportRepository;
import fontys.s3.backend.persistence.entity.AirportEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllAirportsUseCaseImplTest {
    @Mock
    private AirportRepository airportRepository;
    @InjectMocks
    private GetAllAirportsUseCaseImpl getAirportsUseCase;

    @Test
    void getAllAirports_ShouldReturnAllAirports() {
        // Arrange
        AirportEntity airport1 = AirportEntity.builder()
                .iata("AMS")
                .name("Amsterdam Airport Schiphol")
                .build();
        AirportEntity airport2 = AirportEntity.builder()
                .iata("LON")
                .name("London Heathrow Airport")
                .build();
        final List<AirportEntity> airports = List.of(airport1, airport2);
        when(airportRepository.findAll()).thenReturn(airports);

        // Act
        final GetAllAirportsResponse response = getAirportsUseCase.getAllAirports();

        // Assert
        assertEquals(2, response.getAirports().size());
        assertEquals("Amsterdam Airport Schiphol", response.getAirports().get(0).getName());
        assertEquals("London Heathrow Airport", response.getAirports().get(1).getName());
    }

    @Test
    void getAllAirports_ShouldReturnEmptyList_WhenNoAirportsAreFound() {
        // Arrange
        List<AirportEntity> airports = List.of();
        when(airportRepository.findAll()).thenReturn(airports);

        // Act
        final GetAllAirportsResponse response = getAirportsUseCase.getAllAirports();

        // Assert
        assertEquals(0, response.getAirports().size());
    }
}
