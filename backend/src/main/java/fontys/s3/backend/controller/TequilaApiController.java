package fontys.s3.backend.controller;

import fontys.s3.backend.business.usecase.airport.GetAirportByCityUseCase;
import fontys.s3.backend.business.usecase.airport.GetAirportByCordsUseCase;
import fontys.s3.backend.business.usecase.airport.GetTopTenDestinationsByCityUseCase;
import fontys.s3.backend.business.usecase.flight.GetAdvanceSearchFlightsUseCase;
import fontys.s3.backend.business.usecase.flight.GetAllFlightsFromOriginToDestinationUseCase;
import fontys.s3.backend.business.usecase.flight.GetTopThreeCheapestFlightsFromUserLocationUseCase;
import fontys.s3.backend.domain.request.*;
import fontys.s3.backend.domain.response.GetAirportResponse;
import fontys.s3.backend.domain.response.GetFlightsResponse;
import fontys.s3.backend.domain.response.GetTopTenDestinationsResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/tequila")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@AllArgsConstructor
public class TequilaApiController {
    private final GetAllFlightsFromOriginToDestinationUseCase getAllFlightsFromOriginToDestinationUseCase;
    private final GetTopThreeCheapestFlightsFromUserLocationUseCase getTopThreeCheapestFlightsFromUserLocationUseCase;
    private final GetAirportByCityUseCase getAirportByCityUseCase;
    private final GetAirportByCordsUseCase getAirportByCordsUseCase;
    private final GetAdvanceSearchFlightsUseCase getAdvanceSearchFlightsUseCase;
    private final GetTopTenDestinationsByCityUseCase getTopTenDestinationsByCityUseCase;


    @GetMapping("/flights")
    public ResponseEntity<GetFlightsResponse> getAllFlightsFromOriginToDestination(@Valid GetAllFlightsFromOriginToDestinationRequest request) {
            GetFlightsResponse response = getAllFlightsFromOriginToDestinationUseCase.getAllFlightsFromOriginToDestination(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/locations/city")
    public ResponseEntity<GetAirportResponse> getAirportByCity(@RequestParam(value = "city", required = false) String city) {
        GetAirportByCityRequest request = GetAirportByCityRequest.builder()
                .city(city)
                .build();
        GetAirportResponse response = getAirportByCityUseCase.getAirportByCity(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/locations/top-destinations")
    public ResponseEntity<GetTopTenDestinationsResponse> getTopTenDestinationsByCity(@RequestParam(value = "city", required = false) String city) {
        GetTopTenDestinationsResponse response = getTopTenDestinationsByCityUseCase.getTopTenDestinationsByCity(city);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/locations/cords")
    public ResponseEntity<GetAirportResponse> getAirportByCords(@RequestParam(value = "lat", required = false) String lat,
                                                               @RequestParam(value = "lng", required = false) String lng) {
        GetAirportByCordsRequest request = GetAirportByCordsRequest.builder()
                .latitude(lat)
                .longitude(lng)
                .build();
        GetAirportResponse response = getAirportByCordsUseCase.getAirportByCords(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/flights/cheapest")
    public ResponseEntity<GetFlightsResponse> getTopThreeCheapestFlightsFromUserLocation(@Valid GetTopThreeCheapestFlightsFromUserLocationRequest request) {
        GetFlightsResponse response = getTopThreeCheapestFlightsFromUserLocationUseCase.getTopThreeCheapestFlightsFromUserLocation(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/flights/advance")
    public ResponseEntity<GetFlightsResponse> getAdvanceSearchFlights(@Valid GetAdvanceSearchFlightsRequest request) {
        GetFlightsResponse response = getAdvanceSearchFlightsUseCase.getAdvanceSearchFlights(request);
        return ResponseEntity.ok(response);
    }
}
