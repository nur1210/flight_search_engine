package fontys.s3.backend.business.impl.airport;

import fontys.s3.backend.domain.model.Destination;
import fontys.s3.backend.domain.response.GetTopTenDestinationsResponse;
import fontys.s3.backend.persistence.tequilaapi.TequilaAirportsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetTopTenDestinationsByCityUseCaseImplTest {

    @Mock
    private TequilaAirportsRepository tequilaAirportsRepository;

    @InjectMocks
    private GetTopTenDestinationsByCityUseCaseImpl getTopTenDestinationsByCityUseCase;
    @Test
    void getTopTenDestinationsByCity_ShouldReturnTopTenDestinations() {
        // Arrange
        List<Destination> destinations = List.of(
                Destination.builder().city("Paris").build(),
                Destination.builder().city("Barcelona").build(),
                Destination.builder().city("Rome").build(),
                Destination.builder().city("Tel aviv").build(),
                Destination.builder().city("London").build(),
                Destination.builder().city("Berlin").build(),
                Destination.builder().city("Istanbul").build(),
                Destination.builder().city("New York").build(),
                Destination.builder().city("Los Angeles").build(),
                Destination.builder().city("Bangkok").build());

        when(tequilaAirportsRepository.getTopTenDestinationsFromOrigin("Amsterdam")).thenReturn(destinations);

        // Act
        final GetTopTenDestinationsResponse response = getTopTenDestinationsByCityUseCase.getTopTenDestinationsByCity("Amsterdam");

        // Assert
        for (int i = 0; i < destinations.size(); i++) {
            assertEquals(destinations.get(i).getCity(), response.getDestinations().get(i).getCity());
        }
    }

    @Test
    void getTopTenDestinationsByCity_ShouldReturnEmptyList_WhenNoDestinationsAreFound() {
        // Arrange
        final List<Destination> destinations = List.of();
        when(tequilaAirportsRepository.getTopTenDestinationsFromOrigin("Amsterdam")).thenReturn(destinations);

        // Act
        final GetTopTenDestinationsResponse response = getTopTenDestinationsByCityUseCase.getTopTenDestinationsByCity("Amsterdam");

        // Assert
        assertEquals(0, response.getDestinations().size());
    }

    @Test
    void getTopTenDestinationsByCity_ShouldReturnNull_WhenCityIsNull() {
        // Act
        final GetTopTenDestinationsResponse response = getTopTenDestinationsByCityUseCase.getTopTenDestinationsByCity(null);

        // Assert
        assertNotNull(response);
        assertEquals(0, response.getDestinations().size());
    }

    @Test
    void getTopTenDestinationsByCity_ShouldReturnNoDestination_WhenCityIsInvalid() {
        // Arrange
        final List<Destination> destinations = List.of();
        when(tequilaAirportsRepository.getTopTenDestinationsFromOrigin("xyz")).thenReturn(destinations);

        // Act
        final GetTopTenDestinationsResponse response = getTopTenDestinationsByCityUseCase.getTopTenDestinationsByCity("xyz");

        // Assert
        assertEquals(0, response.getDestinations().size());
    }
}

