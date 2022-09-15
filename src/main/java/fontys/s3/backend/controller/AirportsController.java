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
@RequestMapping("/airports")
@AllArgsConstructor
public class AirportsController {

    private final CreateAirportUseCase createCountryUseCase;
    private final GetAirportUseCase getAirportUseCase;
    private final GetAirportsUseCase getAirportsUseCase;
    private final DeleteAirportUseCase deleteAirportUseCase;
    private final UpdateAirportUseCase updateAirportUseCase;


    @GetMapping("{initials}")
    public ResponseEntity<Airport> getAirport(@PathVariable(value = "initials") final String initials){
        final Optional<Airport> airportOptional = getAirportUseCase.getAirport(initials);
        if (airportOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(airportOptional.get());
    }

    @GetMapping
    public ResponseEntity<GetAllAirportsResponse> getAllAirports(){
        GetAllAirportsResponse response = getAirportsUseCase.getAllAirports();
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<CreateAirportResponse> createAirport(@RequestBody @Valid CreateAirportRequest request){
        CreateAirportResponse response = createCountryUseCase.createAirport(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("{initials}")
    public ResponseEntity<Void> deleteAirport(@PathVariable String initials){
        deleteAirportUseCase.deleteAirport(initials);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{initials}")
    public ResponseEntity<Void> updateCountry(@PathVariable("initials") String initials,
                                              @RequestBody @Valid UpdateAirportRequest request){
        request.setInitials(initials);
        updateAirportUseCase.UpdateAirport(request);
        return ResponseEntity.noContent().build();
    }

}
