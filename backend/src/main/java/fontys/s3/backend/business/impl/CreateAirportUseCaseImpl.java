package fontys.s3.backend.business.impl;

import fontys.s3.backend.business.CreateAirportUseCase;
import fontys.s3.backend.business.exception.AirportInitialsAlreadyExistException;
import fontys.s3.backend.domain.CreateAirportRequest;
import fontys.s3.backend.domain.CreateAirportResponse;
import fontys.s3.backend.persistence.AirportRepository;
import fontys.s3.backend.persistence.entity.AirportEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateAirportUseCaseImpl implements CreateAirportUseCase {

    private final AirportRepository airportRepository;
    @Override
    public CreateAirportResponse createAirport(CreateAirportRequest request) {
        if (airportRepository.existsById((request.getIata()))){
            throw new AirportInitialsAlreadyExistException();
        }
        AirportEntity saveAirport = saveNewAirport(request);
        return CreateAirportResponse.builder()
                .iata(saveAirport.getIata())
                .build();
    }

    private AirportEntity saveNewAirport(CreateAirportRequest request){
        AirportEntity newAirport = AirportEntity.builder()
                .iata(request.getIata())
                .city(request.getCity())
                .cityCode(request.getCityCode())
                .country(request.getCountry())
                .countryCode(request.getCountryCode())
                .build();
        return airportRepository.save(newAirport);
    }

}
