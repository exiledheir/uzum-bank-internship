package uz.mkh.service.impl;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.mkh.exception.DataDoesntExistException;
import uz.mkh.exception.InvalidAgeException;
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
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final PersonRepository personRepository;
    private final CarMapper carMapper;
    private final Logger logger = LoggerFactory.getLogger(CarServiceImpl.class);

    /**
     * Fetches amount of cars from database
     *
     * @return Long
     */
    @Override
    @Transactional(readOnly = true)
    public Long getAllCount() {
        logger.info("retrieving car amount");
        List<CarEntity> carList = carRepository.findAll();
        return (long) carList.size();
    }

    /**
     * Fetches distinct vendors from database
     *
     * @return Long
     */
    @Override
    @Transactional(readOnly = true)
    public Long getVendorCount() {
        logger.info("retrieving vendor count");
        return carRepository.countDistinctVendor();
    }

    /**
     * Creates car entity, validates and saves it in database
     *
     * @param request type CarRequest
     * @return ServiceResponse<CarDto>
     */
    @Override
    @Transactional
    public ServiceResponse<CarDto> createCar(@NotNull CarRequest request) {
        logger.info("creating car entity ");

        PersonEntity person = validatePerson(request.getOwnerId());
        validateAge(person.getBirthdate());

        CarEntity car = carMapper.toEntity(request);
        car.setOwner(person);
        car = carRepository.save(car);
        CarDto dto = carMapper.toDto(car);

        logger.info("car created and saved ");
        return ServiceResponse.createSuccess(dto);
    }


    /**
     * Helper method. Validates person if he exists or not
     *
     * @param id type Long
     * @return PersonEntity
     * @throws DataDoesntExistException if person with given id is not found
     */
    private PersonEntity validatePerson(Long id) {
        return personRepository.findById(id).
                orElseThrow(() -> {
                    logger.error("person doesnt exists");
                    return new DataDoesntExistException("Person with id: " + id + ", doesnt exists");
                });
    }

    /**
     * Helper method. Validates if persons age is higher than 18
     *
     * @param birthdate type LocalDate
     * @throws InvalidAgeException if persons age is lower than 18
     */
    private void validateAge(LocalDate birthdate) {
        LocalDate currentDate = LocalDate.now();
        int age = Period.between(birthdate, currentDate).getYears();
        if (age < 18) {
            logger.error("person with age lower than 18 cant own a car");
            throw new InvalidAgeException("Person with age lower 18 cant own a car. Your age: " + age);
        }
    }

}
