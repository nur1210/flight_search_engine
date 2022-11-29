package fontys.s3.backend.controller;

import fontys.s3.backend.business.CreateFlightUseCase;
import fontys.s3.backend.business.DeleteFlightUseCase;
import fontys.s3.backend.business.GetFlightUseCase;
import fontys.s3.backend.business.UpdateFlightUseCase;
import fontys.s3.backend.business.exception.InvalidFlightException;
import fontys.s3.backend.domain.CreateFlightResponse;
import fontys.s3.backend.domain.Flight;
import fontys.s3.backend.domain.UpdateFlightRequest;
import fontys.s3.backend.persistence.FlightRepository;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.webjars.NotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class FlightsControllerTest extends TestCase {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GetFlightUseCase getFlightUseCase;
    @MockBean
    private UpdateFlightUseCase updateFlightUseCase;
    @MockBean
    private DeleteFlightUseCase deleteFlightUseCase;
    @MockBean
    private CreateFlightUseCase createFlightUseCase;
    @Autowired
    private FlightsController flightsController;
    @Mock
    private Flight flight;
    @Mock
    private UpdateFlightRequest updateFlightRequest;

    @BeforeEach
    protected void setUp() {
        flight = mock(Flight.class);
    }

    @Test
    void getFlightSuccessfully() throws Exception {

        //arrange
        when(getFlightUseCase.getFlight(1L)).thenReturn(Optional.of(flight));

        //act
        mockMvc.perform(get("/flights/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        //assert
        verify(getFlightUseCase, times(1)).getFlight(1L);
        verify(getFlightUseCase, never()).getFlight(2L);
    }

    @Test
    void getFlightThatDontExist() throws Exception {

        //arrange
        when(getFlightUseCase.getFlight(1L)).thenReturn(Optional.empty());

        //act
        mockMvc.perform(get("/flights/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        //assert
        verify(getFlightUseCase, times(1)).getFlight(1L);
    }

    @Test
    void deleteFlightWithInvalidIdThrowsException() {
        //TODO how to throw exception when a methode returns void
        doThrow(NotFoundException.class).when(deleteFlightUseCase).deleteFlight(anyLong());

        ResponseEntity<Void> response = flightsController.deleteFlight(anyLong());

        assertThrows(NotFoundException.class, () -> deleteFlightUseCase.deleteFlight(anyLong()));
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteFlightWithValidIdReturn204() {
        FlightRepository repository = mock(FlightRepository.class);
        doNothing().when(deleteFlightUseCase).deleteFlight(anyLong());

        ResponseEntity<Void> response = flightsController.deleteFlight(anyLong());

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(repository, never()).existsById(anyLong());
        verify(repository, never()).deleteById(anyLong());
    }

    @Test
    void createFlight() {
        CreateFlightResponse response = CreateFlightResponse.builder().flightId(1L).build();
        when(createFlightUseCase.createFlight(any())).thenReturn(response);

        long expected = 1L;
        ResponseEntity<CreateFlightResponse> actual = flightsController.createFlight(any());

        assertEquals(actual.getBody().getFlightId(), expected);
        assertEquals(actual.getStatusCode(), HttpStatus.CREATED);

    }

    @Test
    void updateFlightWhenFlightIsUpdatedThenReturn204() {
        doNothing().when(updateFlightUseCase).updateFlight(updateFlightRequest);

        ResponseEntity<Void> response = flightsController.updateFlight(1L, updateFlightRequest);

        assertEquals(204, response.getStatusCodeValue());
        verify(updateFlightUseCase, times(1)).updateFlight(updateFlightRequest);
    }

    @Test
    void updateFlightWhenFlightDoesNotExistThenReturn400() {
        doThrow(InvalidFlightException.class).when(updateFlightUseCase).updateFlight(any());

        try {
            mockMvc.perform(put("/flights/1").contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(InvalidFlightException.class, () -> updateFlightUseCase.updateFlight(updateFlightRequest));
    }
}