package fontys.s3.backend.controller;

import fontys.s3.backend.business.*;
import fontys.s3.backend.business.exception.InvalidFlightException;
import fontys.s3.backend.domain.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/flights")
@AllArgsConstructor
public class FlightsController {
    private final GetFlightUseCase getFlightUseCase;
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

    @DeleteMapping("{flightId}")
    public ResponseEntity<Void> deleteFlight(@PathVariable long flightId) {
        try {
            deleteFlightUseCase.deleteFlight(flightId);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    public ResponseEntity<CreateFlightResponse> createFlight(@RequestBody @Valid CreateFlightRequest request) {
        CreateFlightResponse response = createFlightUseCase.createFlight(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("{flightId}")
    public ResponseEntity<Void> updateFlight(@PathVariable("flightId") long flightId,
                                             @RequestBody @Valid UpdateFlightRequest request) {
        request.setFlightId(flightId);
        try {
            updateFlightUseCase.updateFlight(request);
        } catch (InvalidFlightException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }
}
