package fontys.s3.backend.business.impl;

import fontys.s3.backend.business.CreateAirportUseCase;
import fontys.s3.backend.business.exception.AirportInitialsAlreadyExistException;
import fontys.s3.backend.domain.CreateAirportRequest;
import fontys.s3.backend.domain.CreateAirportResponse;
import fontys.s3.backend.persistence.AddressRepository;
import fontys.s3.backend.persistence.AirportRepository;
import fontys.s3.backend.persistence.entity.AirportEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateAirportUseCaseImpl implements CreateAirportUseCase {

    private final AirportRepository airportRepository;
    private final AddressRepository addressRepository;
    @Override
    public CreateAirportResponse createAirport(CreateAirportRequest request) {
        if (airportRepository.existsById((request.getAirportCode()))){
            throw new AirportInitialsAlreadyExistException();
        }
        AirportEntity saveAirport = saveNewAirport(request);
        return CreateAirportResponse.builder()
                .airportCode(saveAirport.getAirportCode())
                .build();
    }

    private AirportEntity saveNewAirport(CreateAirportRequest request){
        AirportEntity newAirport = AirportEntity.builder()
                .airportCode(request.getAirportCode())
                .name(request.getName())
                .address(addressRepository.findById(request.getAddress().getId()).orElse(addressRepository.save(request.getAddress())))
                .build();
        return airportRepository.save(newAirport);
    }

}
