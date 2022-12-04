package fontys.s3.backend.business.impl;

import fontys.s3.backend.business.impl.flight.GetAllFlightsFromOriginToDestinationUseCaseImpl;
import fontys.s3.backend.domain.request.GetAllFlightsFromOriginToDestinationRequest;
import fontys.s3.backend.persistence.tequilaapi.TequilaFlightsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import java.util.Map;

import static org.mockito.Mockito.mock;

@ContextConfiguration(classes = {GetAllFlightsFromOriginToDestinationUseCaseImpl.class})
@ExtendWith(MockitoExtension.class)
class GetAllFlightsFromOriginToDestinationUseCaseImplTest {
    @Mock
    private TequilaFlightsRepository tequilaFlightsRepository;

    @InjectMocks
    private GetAllFlightsFromOriginToDestinationUseCaseImpl getAllFlightsFromOriginToDestinationUseCase;
    @Mock
    private GetAllFlightsFromOriginToDestinationRequest request;
    @Mock
    Map<String, Object> params = mock(Map.class);


    @BeforeEach
    public void setUp() {
        tequilaFlightsRepository = mock(TequilaFlightsRepository.class);
        request = GetAllFlightsFromOriginToDestinationRequest.builder()
                .flyFrom("AMS")
                .flyTo("LHR")
                .dateFrom("01/01/2021")
                .dateTo("01/01/2021")
                .returnFrom("01/01/2021")
                .returnTo("01/01/2021")
                .flightType("round")
                .adults(1)
                .selectedCabins("M")
                .currency("EUR")
                .language("en")
                .maxStopovers(0)
                .maxSectorStopovers(0)
                .build();
    }

/*    @Test
    void getAllFlightsFromOriginToDestination() {
        //Arrange
        when(tequilaFlightsRepository.getFlightsInfo(params))
                .thenReturn(null);
        //Act
        GetAllFlightsFromOriginToDestinationResponse response = getAllFlightsFromOriginToDestinationUseCase.getAllFlightsFromOriginToDestination(any());

        //Assert
        verify(tequilaFlightsRepository, times(1)).getFlightsInfo(params);
        Assertions.assertNull(response);
    }


    @Test
    void testGetAllFlightsFromOriginToDestination() {
        params=new HashMap<>();

        when(tequilaFlightsRepository.getFlightsInfo((params)))
                .thenReturn(new ArrayList<>());


        GetAllFlightsFromOriginToDestinationResponse actualAllFlightsFromOriginToDestination = getAllFlightsFromOriginToDestinationUseCase
                .getAllFlightsFromOriginToDestination(request);
        assertTrue(actualAllFlightsFromOriginToDestination.getFlights().isEmpty());
        verify(tequilaFlightsRepository).getFlightsInfo(params);
    }*/

/*
    @Test
    void testGetAllFlightsFromOriginToDestinationsdfs() {
        when(tequilaFlightsRepository.getFlightsInfo(any()))
                .thenThrow(new IllegalArgumentException());

        assertThrows(IllegalArgumentException.class,
                () -> getAllFlightsFromOriginToDestinationUseCase.getAllFlightsFromOriginToDestination(request));
        verify(tequilaFlightsRepository).getFlightsInfo(any());
    }*/
}