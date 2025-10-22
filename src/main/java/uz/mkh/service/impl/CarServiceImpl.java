package uz.mkh.service.impl;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import uz.mkh.mapper.CarMapper;
import uz.mkh.model.dto.CarDto;
import uz.mkh.model.entity.CarEntity;
import uz.mkh.model.entity.PersonEntity;
import uz.mkh.model.request.CarRequest;
import uz.mkh.model.response.ServiceResponse;
import uz.mkh.repository.CarRepository;
import uz.mkh.repository.PersonRepository;
import uz.mkh.service.CarService;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository caRepository;
    private final PersonRepository personRepository;
    private final CarMapper carMapper;

    @Override
    public Long getAllCount() {
        List<CarEntity> carList = caRepository.findAll();
        return (long) carList.size();
    }

    @Override
    public ServiceResponse<CarDto> createCar(@NotNull CarRequest request) {

        PersonEntity person = personRepository.findById(request.getOwnerId()).orElseThrow(() -> new NotFoundException("Ownder with this id not found"));

        if (LocalDate.now().compareTo(person.getBirthdate()) < 18)
            throw new IllegalArgumentException("Person with age lower 18 cant buy car");

        CarEntity car = carMapper.toEntity(request);
        car.setOwner(person);
        car = caRepository.save(car);
        CarDto dto = carMapper.toDto(car);

        return ServiceResponse.createSuccess(dto);
    }
}
