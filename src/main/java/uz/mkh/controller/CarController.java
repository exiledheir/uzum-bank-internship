package uz.mkh.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.mkh.model.dto.CarDto;
import uz.mkh.model.request.CarRequest;
import uz.mkh.model.response.ServiceResponse;
import uz.mkh.service.CarService;

@Valid
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Car Management", description = "Manages cars in database")
public class CarController {
    private final CarService service;

    @Operation(summary = "Creates car entity and saves it in database", description = "Returns empty body")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created"),
            @ApiResponse(responseCode = "400", description = "Bad request, validation error")}
    )
    @PostMapping("/car")
    public ResponseEntity<Void> createCar(@RequestBody @Valid CarRequest request) {
        ServiceResponse<CarDto> response = service.createCar(request);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
