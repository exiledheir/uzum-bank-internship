package uz.mkh.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
import uz.mkh.service.impl.CarServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarServiceWithMocksTest {

    @Mock
    private CarRepository carRepository;
    @Mock
    private CarMapper carMapper;
    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private CarServiceImpl carServiceImpl;

    private PersonEntity personEntity;
    private PersonEntity personLower18;
    private CarRequest carRequest;
    private CarEntity carEntity;
    private CarDto carDto;

    private Validator validator;
    private Set<ConstraintViolation<CarRequest>> validate;

    @BeforeEach
    void setUp() {
        personEntity = PersonEntity.builder()
                .id(1L)
                .name("Mukhammadjon")
                .birthdate(LocalDate.of(2002, 12, 30))
                .build();
        personLower18 = PersonEntity.builder()
                .id(2L)
                .name("Sardor")
                .birthdate(LocalDate.of(2012, 12, 30))
                .build();
        carRequest = CarRequest.builder()
                .model("Kia-K5")
                .horsePower(399L)
                .ownerId(1L)
                .build();
        carEntity = CarEntity.builder()
                .id(1L)
                .model("Kia-K5")
                .horsePower(399L)
                .owner(personEntity)
                .build();
        carDto = CarDto.builder()
                .id(1L)
                .model("Kia-K5")
                .horsePower(399L)
                .ownerId(1L)
                .build();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void createCarSuccessful() {
        when(personRepository.findById(1L)).thenReturn(Optional.of(personEntity));
        when(carMapper.toEntity(carRequest)).thenReturn(carEntity);
        when(carRepository.save(any(CarEntity.class))).thenReturn(carEntity);
        when(carMapper.toDto(carEntity)).thenReturn(carDto);

        ServiceResponse<CarDto> response = carServiceImpl.createCar(carRequest);

        assertEquals(carRequest.getModel(), response.getPayload().getModel());
        assertEquals(carRequest.getHorsePower(), response.getPayload().getHorsePower());
        assertEquals(carRequest.getOwnerId(), response.getPayload().getOwnerId());

    }

    @Test
    void createCarWithNotExistingUser() {
        when(personRepository.findById(100L)).thenReturn(Optional.empty());

        CarRequest request = CarRequest.builder()
                .model("Kia-K5")
                .horsePower(399L)
                .ownerId(100L)
                .build();

        assertThrows(DataDoesntExistException.class, () -> carServiceImpl.createCar(request));
    }

    @Test
    void createCarWithUserAgeLower18() {
        when(personRepository.findById(2L)).thenReturn(Optional.of(personLower18));

        CarRequest request = CarRequest.builder()
                .model("Kia-K5")
                .horsePower(399L)
                .ownerId(2L)
                .build();

        assertThrows(InvalidAgeException.class, () -> carServiceImpl.createCar(request));
    }

    @Test
    void createCarWithNotValidModel() {
        CarRequest request = CarRequest.builder()
                .model(null)
                .horsePower(399L)
                .ownerId(2L)
                .build();

        validate = validator.validate(request);
        assertFalse(validate.isEmpty());
    }

    @Test
    void createCarWithNotValidHorsePower() {
        CarRequest request = CarRequest.builder()
                .model("null")
                .horsePower(-1L)
                .ownerId(2L)
                .build();

        validate = validator.validate(request);
        assertFalse(validate.isEmpty());
    }

    @Test
    void getAllCarCountSuccess() {
        List<CarEntity> storedCars = List.of(carEntity);
        when(carRepository.findAll()).thenReturn(storedCars);
        long response = carServiceImpl.getAllCount();
        assertEquals(storedCars.size(), response);
    }

    @Test
    void getAllVendorCountSuccess() {
        when(carRepository.countDistinctVendor()).thenReturn(1L);
        long response = carServiceImpl.getVendorCount();

        assertEquals(1L, response);
    }
}