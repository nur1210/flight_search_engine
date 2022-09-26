package fontys.s3.backend.controller;

import fontys.s3.backend.business.GetAllFlightsFromOriginToDestinationUseCase;
import fontys.s3.backend.domain.GetAllFlightsFromOriginToDestinationRequest;
import fontys.s3.backend.domain.GetAllFlightsFromOriginToDestinationResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tequila")
@AllArgsConstructor
public class TequilaApiController {
    private final GetAllFlightsFromOriginToDestinationUseCase getAllFlightsFromOriginToDestinationUseCase;


    @GetMapping
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
}
