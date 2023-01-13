package fontys.s3.backend.business.impl.airport;

import fontys.s3.backend.domain.request.GetAirportByCordsRequest;
import fontys.s3.backend.domain.response.GetAirportResponse;
import fontys.s3.backend.persistence.entity.AirportEntity;
import fontys.s3.backend.persistence.tequilaapi.TequilaAirportsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAirportByCordsUseCaseImplTest {
    @Mock
    private TequilaAirportsRepository tequilaAirportsRepository;

    @InjectMocks
    private GetAirportByCordsUseCaseImpl getAirportByCordsUseCase;

    @Test
    void getAirportByCords_ShouldReturnAirportByCords() {
        // Arrange
        AirportEntity airport = AirportEntity.builder()
                .iata("AMS")
                .name("Amsterdam Airport Schiphol")
                .build();
        when(tequilaAirportsRepository.getAirportByCords("15.2313", "44.12312")).thenReturn(airport);

        // Act
        final GetAirportResponse response = getAirportByCordsUseCase.getAirportByCords(
                GetAirportByCordsRequest.builder()
                        .latitude("15.2313")
                        .longitude("44.12312")
                        .build());

        // Assert
        assertEquals("AMS", response.getAirport().getIata());
        assertEquals("Amsterdam Airport Schiphol", response.getAirport().getName());
    }

}