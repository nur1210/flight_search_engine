package fontys.s3.backend.controller;

import fontys.s3.backend.business.*;
import fontys.s3.backend.domain.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/flights")
@AllArgsConstructor
public class FlightsController {
    private final GetFlightUseCase getFlightUseCase;
    private final GetAllFlightsFromOriginToDestinationUseCase getFlightsUseCase;
    private final DeleteFlightUseCase deleteFlightUseCase;
    private final CreateFlightUseCase createFlightUseCase;
    private final UpdateFlightUseCase updateFlightUseCase;

    @GetMapping("{flightId}")
    public ResponseEntity<Flight> getFlight(@PathVariable(value = "flightId") final long flightId) {
        final Optional<Flight> flightOptional = getFlightUseCase.getFlight(flightId);
        if (flightOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(flightOptional.get());
    }

    @GetMapping
    public ResponseEntity<GetAllFlightsFromOriginToDestinationResponse> getAllFlights(@RequestParam(value = "fly_from", required = false) String flyFrom,
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
        GetAllFlightsFromOriginToDestinationResponse response = getFlightsUseCase.getFlights(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{flightId}")
    public ResponseEntity<Void> deleteFlight(@PathVariable long flightId) {
        deleteFlightUseCase.deleteFlight(flightId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping()
    public ResponseEntity<CreateFlightResponse> createFlight(@RequestBody @Valid CreateFlightRequest request) {
        CreateFlightResponse response = createFlightUseCase.CreateFlight(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("{flightId}")
    public ResponseEntity<Void> updateFlight(@PathVariable("flightId") long flightId,
                                             @RequestBody @Valid UpdateFlightRequest request) {
        request.setFlightId(flightId);
        updateFlightUseCase.updateFlight(request);
        return ResponseEntity.noContent().build();
    }
}
