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


    @GetMapping("{iata}")
    public ResponseEntity<Airport> getAirport(@PathVariable(value = "iata") final String iata){
        final Optional<Airport> airportOptional = getAirportUseCase.getAirport(iata);
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

    @DeleteMapping("{iata}")
    public ResponseEntity<Void> deleteAirport(@PathVariable String iata){
        deleteAirportUseCase.deleteAirport(iata);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{iata}")
    public ResponseEntity<Void> updateCountry(@PathVariable("iata") String iata,
                                              @RequestBody @Valid UpdateAirportRequest request){
        request.setIata(iata);
        updateAirportUseCase.updateAirport(request);
        return ResponseEntity.noContent().build();
    }

}
