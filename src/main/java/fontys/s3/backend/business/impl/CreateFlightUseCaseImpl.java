package fontys.s3.backend.business.impl;

import fontys.s3.backend.business.CreateFlightUseCase;
import fontys.s3.backend.business.exception.FlightNumberAlreadyExistsException;
import fontys.s3.backend.domain.CreateFlightRequest;
import fontys.s3.backend.domain.CreateFlightResponse;
import fontys.s3.backend.persistence.FlightRepository;
import fontys.s3.backend.persistence.entity.AirportEntity;
import fontys.s3.backend.persistence.entity.FlightEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateFlightUseCaseImpl implements CreateFlightUseCase {
    private final FlightRepository flightRepository;

    @Override
    public CreateFlightResponse CreateFlight(CreateFlightRequest request) {
        if (flightRepository.existsById(request.getAirline() + request.getFlight_no())) {
            throw new FlightNumberAlreadyExistsException();
        }

        FlightEntity saveFlight = saveNewFlight(request);

        return CreateFlightResponse.builder()
                .flightNumber(saveFlight.getFlightNumber())
                .build();
    }

    private FlightEntity saveNewFlight(CreateFlightRequest request) {
        FlightEntity newFlight = FlightEntity.builder()
                .flightNumber(request.getAirline() + request.getFlight_no())
                .airline(request.getAirline())
                .departureAirport(AirportEntity.builder()
                        .iata(request.getFlyFrom())
                        .city(request.getCityFrom())
                        .cityCode(request.getCityCodeFrom())
                        .country(request.getCountryFrom().getName())
                        .countryCode(request.getCountryFrom().getCode())
                        .build())
                .arrivalAirport(AirportEntity.builder()
                        .iata(request.getFlyTo())
                        .city(request.getCityTo())
                        .cityCode(request.getCityCodeTo())
                        .country(request.getCountryTo().getName())
                        .countryCode(request.getCountryTo().getCode())
                        .build())
                .localDepartureTime(request.getLocal_departure())
                .utcDepartureTime(request.getUtc_departure())
                .localArrivalTime(request.getLocal_arrival())
                .utcArrivalTime(request.getUtc_arrival())
                .economicPrice(request.getEconomicPrice())
                .businessPrice(request.getBusinessPrice())
                .build();

        return flightRepository.save(newFlight);
    }
}
