package fontys.s3.backend.business.impl;

import fontys.s3.backend.persistence.FlightRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {DeleteFlightUseCaseImpl.class})
@ExtendWith(SpringExtension.class)
class DeleteFlightUseCaseImplTest {
    @Autowired
    private DeleteFlightUseCaseImpl deleteFlightUseCaseImpl;

    @MockBean
    private FlightRepository flightRepository;

    @Test
    void testDeleteFlight() {
        doNothing().when(flightRepository).deleteById(any());
        long flightId = 123L;
        deleteFlightUseCaseImpl.deleteFlight(flightId);
        verify(flightRepository).deleteById(any());
    }
}

