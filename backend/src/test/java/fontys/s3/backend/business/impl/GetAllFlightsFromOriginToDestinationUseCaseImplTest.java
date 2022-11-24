package fontys.s3.backend.business.impl;

import fontys.s3.backend.domain.GetAllFlightsFromOriginToDestinationRequest;
import fontys.s3.backend.domain.GetAllFlightsFromOriginToDestinationResponse;
import fontys.s3.backend.persistence.tequilaapi.TequilaFlightsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {GetAllFlightsFromOriginToDestinationUseCaseImpl.class})
@ExtendWith(MockitoExtension.class)
class GetAllFlightsFromOriginToDestinationUseCaseImplTest {

    @Autowired
    private GetAllFlightsFromOriginToDestinationUseCaseImpl getAllFlightsFromOriginToDestinationUseCaseImpl;

    @Mock
    private TequilaFlightsRepository tequilaFlightsRepository;

    @InjectMocks
    private GetAllFlightsFromOriginToDestinationUseCaseImpl getAllFlightsFromOriginToDestinationUseCase;
    @Mock
    private GetAllFlightsFromOriginToDestinationRequest request;
    @Mock
    Map<String, Object> params;

    @BeforeEach
    public void setUp() {
        tequilaFlightsRepository = mock(TequilaFlightsRepository.class);
        getAllFlightsFromOriginToDestinationUseCase = new GetAllFlightsFromOriginToDestinationUseCaseImpl(tequilaFlightsRepository);
        request = GetAllFlightsFromOriginToDestinationRequest.builder()
                .fly_from("AMS")
                .fly_to("LHR")
                .date_from("01/01/2021")
                .date_to("01/01/2021")
                .return_from("01/01/2021")
                .return_to("01/01/2021")
                .flight_type("round")
                .adults(1)
                .selected_cabins("M")
                .curr("EUR")
                .locale("en")
                .max_stopovers(0)
                .max_sector_stopovers(0)
                .build();
    }

    @Test
    void getAllFlightsFromOriginToDestination() {
        //Arrange
        when(tequilaFlightsRepository.getFlightsInfo(params)).thenReturn(null);
        //Act
        GetAllFlightsFromOriginToDestinationResponse response = getAllFlightsFromOriginToDestinationUseCase.getAllFlightsFromOriginToDestination(any());

        //Assert
        verify(tequilaFlightsRepository, times(1)).getFlightsInfo(params);
        Assertions.assertNull(response);
    }


    @Test
    void testGetAllFlightsFromOriginToDestination() {
        when(tequilaFlightsRepository.getFlightsInfo(any()))
                .thenReturn(new ArrayList<>());

        GetAllFlightsFromOriginToDestinationResponse actualAllFlightsFromOriginToDestination = getAllFlightsFromOriginToDestinationUseCaseImpl
                .getAllFlightsFromOriginToDestination(request);
        assertTrue(actualAllFlightsFromOriginToDestination.getFlights().isEmpty());
        verify(tequilaFlightsRepository).getFlightsInfo(any());
    }


    @Test
    void testGetAllFlightsFromOriginToDestination3() {
        when(tequilaFlightsRepository.getFlightsInfo(any()))
                .thenThrow(new IllegalArgumentException());

        assertThrows(IllegalArgumentException.class,
                () -> getAllFlightsFromOriginToDestinationUseCaseImpl.getAllFlightsFromOriginToDestination(request));
        verify(tequilaFlightsRepository).getFlightsInfo(any());
    }
}