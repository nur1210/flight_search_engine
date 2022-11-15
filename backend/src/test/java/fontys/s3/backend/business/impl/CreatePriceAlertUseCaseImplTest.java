package fontys.s3.backend.business.impl;

import fontys.s3.backend.business.exception.UnauthorizedDataAccessException;
import fontys.s3.backend.domain.AccessToken;
import fontys.s3.backend.domain.CreatePriceAlertRequest;
import fontys.s3.backend.domain.CreatePriceAlertResponse;
import fontys.s3.backend.persistence.PriceAlertRepository;
import fontys.s3.backend.persistence.UserRepository;
import fontys.s3.backend.persistence.entity.PriceAlertEntity;
import fontys.s3.backend.persistence.entity.RoleEnum;
import fontys.s3.backend.persistence.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreatePriceAlertUseCaseImplTest {
    @Mock
    private PriceAlertRepository priceAlertRepository;
    @Mock
    private UserRepository userRepository;
    @Spy
    private AccessToken requestToken;
    @Mock
    private CreatePriceAlertRequest request;
    @InjectMocks
    private CreatePriceAlertUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        request = CreatePriceAlertRequest.builder()
                .flyFrom("AMS")
                .flyTo("LHR")
                .dateFrom(new Date())
                .dateTo(new Date())
                .flightType("round")
                .passengers(1)
                .cabinClass("W")
                .currency("EUR")
                .locale("en")
                .build();
    }

    @Test
    void createPriceAlert() {
        //arrange
        UserEntity user = UserEntity.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@gmail.com")
                .password("password")
                .build();

        PriceAlertEntity priceAlert = PriceAlertEntity.builder()
                .id(1L)
                .users(List.of(user))
                .flyFrom("AMS")
                .flyTo("LHR")
                .dateFrom(new Date())
                .dateTo(new Date())
                .flightType("round")
                .passengers(1)
                .cabinClass("W")
                .currency("EUR")
                .locale("en")
                .build();

        requestToken = AccessToken.builder()
                .userId(1L)
                .roles(List.of(RoleEnum.USER.name()))
                .build();

        useCase = new CreatePriceAlertUseCaseImpl(priceAlertRepository, userRepository, requestToken);

        when(priceAlertRepository.save(any()))
                .thenReturn(priceAlert);

        //act
        CreatePriceAlertResponse expected = CreatePriceAlertResponse.builder()
                .id(1L)
                .build();

        CreatePriceAlertResponse actual = useCase.createPriceAlert(request);

        //assert
        Assertions.assertEquals(expected.getId(), actual.getId());
        Assertions.assertEquals(expected, actual);
    }


    @Test
    void createPriceAlertWithInvalidToken() {
        //arrange
        requestToken = AccessToken.builder()
                .userId(1L)
                .roles(null)
                .build();

        useCase = new CreatePriceAlertUseCaseImpl(priceAlertRepository, userRepository, requestToken);

        //act
        var exception = Assertions.assertThrows(UnauthorizedDataAccessException.class,
                () -> useCase.createPriceAlert(request));

        //assert
        Assertions.assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());
    }
}