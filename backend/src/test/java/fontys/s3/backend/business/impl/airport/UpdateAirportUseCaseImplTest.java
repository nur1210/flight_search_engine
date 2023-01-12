package fontys.s3.backend.business.impl.airport;

import fontys.s3.backend.domain.request.UpdateAirportRequest;
import fontys.s3.backend.persistence.AirportRepository;
import fontys.s3.backend.persistence.entity.AirportEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Profile("test")
class UpdateAirportUseCaseImplTest {

    @Mock
    private AirportRepository airportRepository;

    @InjectMocks
    private UpdateAirportUseCaseImpl updateAirportUseCase;

    @Test
    void updateAirport_ShouldUpdateTheAirport() {
        // Arrange
        UpdateAirportRequest request = UpdateAirportRequest.builder()
                .iata("AMS")
                .build();
        AirportEntity airport = AirportEntity.builder()
                .iata("AMS")
                .name("Amsterdam Airport")
                .build();
        when(airportRepository.findById("AMS")).thenReturn(Optional.of(airport));
        // Act
        updateAirportUseCase.updateAirport(request);

        // Assert
        verify(airportRepository).findById("AMS");
        verify(airportRepository).save(airport);
        assertEquals("Amsterdam Airport", airport.getName());
    }

    @Test
    void updateAirport_ShouldThrowRuntimeException_WhenAirportNotFound() {
        // Arrange
        UpdateAirportRequest request = UpdateAirportRequest.builder()
                .iata("AMS")
                .build();
        when(airportRepository.findById("AMS")).thenReturn(Optional.empty());
        // Act & Assert
        assertThrows(RuntimeException.class, () -> updateAirportUseCase.updateAirport(request), "AIRPORT_NOT_FOUND");
    }
}