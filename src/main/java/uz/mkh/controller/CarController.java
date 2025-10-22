package uz.mkh.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class CarController {
    private final CarService service;

    @PostMapping("/car")
    public ResponseEntity<Void> createCar(@RequestBody @Valid CarRequest request) {
        ServiceResponse<CarDto> response = service.createCar(request);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
}
