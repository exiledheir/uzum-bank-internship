package uz.mkh.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.mkh.model.dto.CarDto;
import uz.mkh.model.request.CarRequest;
import uz.mkh.model.response.ServiceResponse;
import uz.mkh.service.CarService;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CarController {
    private final CarService service;

    @PostMapping("/car")
    public ResponseEntity<Void> createCar(@RequestBody @Validated CarRequest request){
        System.out.println(request);
        ServiceResponse<CarDto> response = service.createCar(request);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
