package fontys.s3.backend.business.impl;

import fontys.s3.backend.persistence.FlightRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.webjars.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {DeleteFlightUseCaseImpl.class})
@ExtendWith(SpringExtension.class)
class DeleteFlightUseCaseImplTest {
    @Autowired
    private DeleteFlightUseCaseImpl deleteFlightUseCaseImpl;

    @MockBean
    private FlightRepository flightRepository;

    @Test
    void deleteFlightWhenFlightDoesNotExistThenThrowException() {
        when(flightRepository.existsById(1L)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> deleteFlightUseCaseImpl.deleteFlight(1L));
    }

    @Test
    void deleteFlightWhenFlightExists() {
        long flightId = 1L;
        when(flightRepository.existsById(flightId)).thenReturn(true);

        deleteFlightUseCaseImpl.deleteFlight(flightId);

        verify(flightRepository, times(1)).deleteById(flightId);
    }
}

