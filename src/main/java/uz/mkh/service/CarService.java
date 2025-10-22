package uz.mkh.service;

import jakarta.validation.constraints.NotNull;
import uz.mkh.model.dto.CarDto;
import uz.mkh.model.request.CarRequest;
import uz.mkh.model.response.ServiceResponse;

public interface CarService {
    Long getAllCount();

    ServiceResponse<CarDto> createCar(@NotNull CarRequest request);

    void clearData();
}
