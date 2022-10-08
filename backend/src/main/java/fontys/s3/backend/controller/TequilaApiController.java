package fontys.s3.backend.controller;

import fontys.s3.backend.business.GetAirportByCityUseCase;
import fontys.s3.backend.business.GetAirportByCordsUseCase;
import fontys.s3.backend.business.GetAllFlightsFromOriginToDestinationUseCase;
import fontys.s3.backend.domain.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tequila")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class TequilaApiController {
    private final GetAllFlightsFromOriginToDestinationUseCase getAllFlightsFromOriginToDestinationUseCase;
    private final GetAirportByCityUseCase getAirportByCityUseCase;
    private final GetAirportByCordsUseCase getAirportByCordsUseCase;


    @GetMapping("/flights")
    public ResponseEntity<GetAllFlightsFromOriginToDestinationResponse> getAllFlightsFromOriginToDestination(@RequestParam(value = "fly_from", required = false) String flyFrom,
                                                                                                             @RequestParam(value = "fly_to", required = false) String flyTo,
                                                                                                             @RequestParam(value = "date_from", required = false) String dateFrom,
                                                                                                             @RequestParam(value = "date_to", required = false) String dateTo,
                                                                                                             @RequestParam(value = "return_from", required = false) String returnFrom,
                                                                                                             @RequestParam(value = "return_to", required = false) String returnTo,
                                                                                                             @RequestParam(value = "flight_type", required = false) String flightType,
                                                                                                             @RequestParam(value = "adults", required = false) long adults,
                                                                                                             @RequestParam(value = "selected_cabins", required = false) String selectedCabins,
                                                                                                             @RequestParam(value = "curr", required = false) String curr,
                                                                                                             @RequestParam(value = "locale", required = false) String locale,
                                                                                                             @RequestParam(value = "max_stopovers", required = false) long maxStopovers,
                                                                                                             @RequestParam(value = "max_sector_stopovers", required = false) long maxSectorStopovers) {
        GetAllFlightsFromOriginToDestinationRequest request = GetAllFlightsFromOriginToDestinationRequest.builder()
                .fly_from(flyFrom)
                .fly_to(flyTo)
                .date_from(dateFrom)
                .date_to(dateTo)
                .return_from(returnFrom)
                .return_to(returnTo)
                .flight_type(flightType)
                .adults(adults)
                .selected_cabins(selectedCabins)
                .curr(curr)
                .locale(locale)
                .max_stopovers(maxStopovers)
                .max_sector_stopovers(maxSectorStopovers)
                .build();
        GetAllFlightsFromOriginToDestinationResponse response = getAllFlightsFromOriginToDestinationUseCase.getAllFlightsFromOriginToDestination(request);
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

    @GetMapping("/locations/cords")
    public ResponseEntity<GetAirportResponse> getAirportByCity(@RequestParam(value = "lat", required = false) String lat,
                                                              @RequestParam(value = "lng", required = false) String lng) {
        GetAirportByCordsRequest request = GetAirportByCordsRequest.builder()
                .latitude(lat)
                .longitude(lng)
                .build();
        GetAirportResponse response = getAirportByCordsUseCase.getAirportByCords(request);
        return ResponseEntity.ok(response);
    }
}
