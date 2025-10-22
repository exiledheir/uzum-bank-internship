package uz.mkh.service.impl;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.mkh.exception.CarAlreadyExistsException;
import uz.mkh.exception.DataDoesntExistException;
import uz.mkh.exception.InvalidAgeException;
import uz.mkh.exception.PersonNotFoundException;
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

    private final CarRepository carRepository;
    private final PersonRepository personRepository;
    private final CarMapper carMapper;

    @Override
    public Long getAllCount() {
        List<CarEntity> carList = carRepository.findAll();
        return (long) carList.size();
    }

    @Override
    @Transactional
    public ServiceResponse<CarDto> createCar(@NotNull CarRequest request) {
        if (carRepository.existsById(request.getId()))
            throw new CarAlreadyExistsException("Car with id: " + request.getId() + ", already exists");

        PersonEntity person = personRepository.findById(request.getOwnerId()).
                orElseThrow(() -> new DataDoesntExistException("Person with id: " + request.getOwnerId() + ", doesnt exists"));

        int age = LocalDate.now().compareTo(person.getBirthdate());
        if (age < 18)
            throw new InvalidAgeException("Person with age lower 18 cant own a car. Your age: " + (age > 0 ? age : 0));
        CarEntity car = carMapper.toEntity(request);
        car.setOwner(person);
        car = carRepository.save(car);
        CarDto dto = carMapper.toDto(car);

        return ServiceResponse.createSuccess(dto);
    }

    public void clearData(){
        carRepository.deleteAllInBatch();
    }
}
