package uz.mkh;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import uz.mkh.exception.CarAlreadyExistsException;
import uz.mkh.exception.DataDoesntExistException;
import uz.mkh.exception.InvalidAgeException;
import uz.mkh.model.request.CarRequest;
import uz.mkh.model.request.PersonRequest;
import uz.mkh.service.CarService;
import uz.mkh.service.PersonService;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class CarServiceTest {

    @Autowired
    private CarService carService;

    @Autowired
    private PersonService personService;


    @BeforeEach
    void setUp() {
        PersonRequest personRequest = PersonRequest.builder()
                .id(1L)
                .name("Mukhammadjon")
                .birthdate(LocalDate.of(2002, 12, 30))
                .build();

        PersonRequest personLower18 = PersonRequest.builder()
                .id(2L)
                .name("Sardor")
                .birthdate(LocalDate.of(2012, 12, 30))
                .build();
        personService.createPerson(personRequest);
        personService.createPerson(personLower18);

        CarRequest car1 = CarRequest.builder()
                .id(1L)
                .model("Kia-K5")
                .horsePower(399L)
                .ownerId(1L)
                .build();
        carService.createCar(car1);

    }

    @Test
    void createCarSuccessful() {
        long initial = carService.getAllCount();
        CarRequest request = CarRequest.builder()
                .id(2L)
                .model("Kia-K5")
                .horsePower(399L)
                .ownerId(1L)
                .build();
        carService.createCar(request);

        assertEquals(carService.getAllCount(), initial + 1);
    }

    @Test
    void createCarWithNotExistingUser() {
        CarRequest request = CarRequest.builder()
                .id(2L)
                .model("Kia-K5")
                .horsePower(399L)
                .ownerId(100L)
                .build();


        assertThatExceptionOfType(DataDoesntExistException.class).isThrownBy(() -> {
            carService.createCar(request);
        }).withMessage("Person with id: 100, doesnt exists");
    }

    @Test
    void createCarWithUserAgeLower18() {
        CarRequest request = CarRequest.builder()
                .id(2L)
                .model("Kia-K5")
                .horsePower(399L)
                .ownerId(2L)
                .build();

        assertThatExceptionOfType(InvalidAgeException.class).isThrownBy(() -> {
            carService.createCar(request);
        });
    }

    @Test
    void createExistingCar() {
        CarRequest request = CarRequest.builder()
                .id(1L)
                .model("Kia-K5")
                .horsePower(399L)
                .ownerId(2L)
                .build();
        assertThatExceptionOfType(CarAlreadyExistsException.class).isThrownBy(() -> {
            carService.createCar(request);
        }).withMessage("Car with id: 1, already exists");
    }

}