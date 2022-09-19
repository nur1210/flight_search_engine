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
    private final GetFlightsUseCase getFlightsUseCase;
    private final DeleteFlightUseCase deleteFlightUseCase;
    private final CreateFlightUseCase createFlightUseCase;
    private final UpdateFlightUseCase updateFlightUseCase;

    @GetMapping("{flightNumber}")
    public ResponseEntity<Flight> getFlight(@PathVariable(value = "flightNumber") final String flightNumber) {
        final Optional<Flight> flightOptional = getFlightUseCase.getFlight(flightNumber);
        if (flightOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(flightOptional.get());
    }

    @GetMapping
    public ResponseEntity<GetAllFlightsResponse> getAllFlights(@RequestParam(value = "origin", required = false) String origin,
                                                               @RequestParam(value = "destination", required = false) String destination) {
        GetAllFlightsRequest request = GetAllFlightsRequest.builder()
                .origin(origin)
                .destination(destination)
                .build();
        GetAllFlightsResponse response = getFlightsUseCase.getFlights(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{flightNumber}")
    public ResponseEntity<Void> deleteFlight(@PathVariable String flightNumber) {
        deleteFlightUseCase.deleteFlight(flightNumber);
        return ResponseEntity.noContent().build();
    }

    @PostMapping()
    public ResponseEntity<CreateFlightResponse> createFlight(@RequestBody @Valid CreateFlightRequest request) {
        CreateFlightResponse response = createFlightUseCase.CreateFlight(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("{flightNumber}")
    public ResponseEntity<Void> updateFlight(@PathVariable("flightNumber") String flightNumber,
                                             @RequestBody @Valid UpdateFlightRequest request) {
        request.setFlightNumber(flightNumber);
        updateFlightUseCase.updateFlight(request);
        return ResponseEntity.noContent().build();
    }
}
