package fontys.s3.backend.controller;

import fontys.s3.backend.business.usecase.pricealert.CreatePriceAlertUseCase;
import fontys.s3.backend.configuration.security.isauthenticated.IsAuthenticated;
import fontys.s3.backend.domain.request.CreatePriceAlertRequest;
import fontys.s3.backend.domain.response.CreatePriceAlertResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@RequestMapping("/alerts")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequiredArgsConstructor
public class PriceAlertController {
    private final CreatePriceAlertUseCase createPriceAlertUseCase;

    @IsAuthenticated
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping()
    public ResponseEntity<CreatePriceAlertResponse> createPriceAlert(@RequestBody @Valid CreatePriceAlertRequest request) {
        CreatePriceAlertResponse response = createPriceAlertUseCase.createPriceAlert(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
