package fontys.s3.backend.business.impl.pricealert;

import fontys.s3.backend.business.exception.UnauthorizedDataAccessException;
import fontys.s3.backend.business.usecase.pricealert.CreatePriceAlertUseCase;
import fontys.s3.backend.domain.model.AccessToken;
import fontys.s3.backend.domain.request.CreatePriceAlertRequest;
import fontys.s3.backend.domain.response.CreatePriceAlertResponse;
import fontys.s3.backend.persistence.PriceAlertRepository;
import fontys.s3.backend.persistence.UserRepository;
import fontys.s3.backend.persistence.entity.PriceAlertEntity;
import fontys.s3.backend.persistence.entity.RoleEnum;
import fontys.s3.backend.persistence.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CreatePriceAlertUseCaseImpl implements CreatePriceAlertUseCase {
    private final PriceAlertRepository priceAlertRepository;
    private final UserRepository userRepository;
    private AccessToken requestToken;

    @Override
    public CreatePriceAlertResponse createPriceAlert(CreatePriceAlertRequest request) {
        if (!requestToken.hasRole(RoleEnum.USER.name())){
            throw new UnauthorizedDataAccessException("USER_IS_NOT_AUTHENTICATED");
        }

        PriceAlertEntity saveAlert = saveNewAlert(request);

        return CreatePriceAlertResponse.builder()
                .id(saveAlert.getId())
                .build();
    }

    private PriceAlertEntity saveNewAlert(CreatePriceAlertRequest request) {
        List<UserEntity> users = userRepository.findById(requestToken.getUserId()).stream().toList();

        PriceAlertEntity newPriceAlert = PriceAlertEntity.builder()
                .users(users)
                .flyFrom(request.getFlyFrom())
                .flyTo(request.getFlyTo())
                .dateFrom(request.getDateFrom())
                .dateTo(request.getDateTo())
                .flightType(request.getFlightType())
                .passengers(request.getPassengers())
                .cabinClass(request.getCabinClass())
                .currency(request.getCurrency())
                .locale(request.getLocale())
                .build();

        return priceAlertRepository.save(newPriceAlert);
    }
}
