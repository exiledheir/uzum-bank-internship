package uz.mkh.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.mkh.model.response.ServiceResponse;
import uz.mkh.model.response.StatisticResponse;
import uz.mkh.repository.CarRepository;
import uz.mkh.service.CarService;
import uz.mkh.service.PersonService;
import uz.mkh.service.StatisticService;

@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {
    private final CarService carService;
    private final PersonService personService;
    private final CarRepository carRepository;

    @Override
    public ServiceResponse<StatisticResponse> getStatistics() {
        Long carCount = carService.getAllCount();
        Long personCount = personService.getAllCount();
        Long vendorCount = carRepository.findAll()
                .stream()
                .map((car) -> car.getModel().split("-")[0])
                .distinct()
                .count();
        StatisticResponse response = StatisticResponse.builder()
                .carCount(carCount)
                .personCount(personCount)
                .uniqueVendorCount(vendorCount)
                .build();

        return ServiceResponse.createSuccess(response);
    }
}
