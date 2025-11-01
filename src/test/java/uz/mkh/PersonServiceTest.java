package uz.mkh;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import uz.mkh.exception.PersonAlreadyExistsException;
import uz.mkh.exception.PersonNotFoundException;
import uz.mkh.model.request.CarRequest;
import uz.mkh.model.request.PersonRequest;
import uz.mkh.model.response.PersonResponse;
import uz.mkh.model.response.ServiceResponse;
import uz.mkh.service.CarService;
import uz.mkh.service.PersonService;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @Autowired
    private CarService carService;

    @BeforeEach
    void setUp() {
        PersonRequest personRequest = PersonRequest.builder()
                .name("Mukhammadjon")
                .birthdate(LocalDate.of(2002, 12, 30))
                .build();
        personService.createPerson(personRequest);

        CarRequest request1 = CarRequest.builder()
                .model("Kia-K5")
                .horsePower(399L)
                .ownerId(1L)
                .build();

        CarRequest request2 = CarRequest.builder()
                .model("BMW-X5")
                .horsePower(343L)
                .ownerId(1L)
                .build();

        carService.createCar(request1);
        carService.createCar(request2);
    }

    @Test
    void createPersonSuccessful() {
        long initial = personService.getAllCount();
        PersonRequest personRequest = PersonRequest.builder()
                .name("Gulomjon")
                .birthdate(LocalDate.of(2023, 12, 30))
                .build();

        personService.createPerson(personRequest);
        assertEquals(carService.getAllCount(), initial + 1);
    }

    @Test
    void createExistingPerson() {
        PersonRequest personRequest = PersonRequest.builder()
                .name("Gulomjon")
                .birthdate(LocalDate.of(2023, 12, 30))
                .build();

        assertThatExceptionOfType(PersonAlreadyExistsException.class).isThrownBy(() -> {
            personService.createPerson(personRequest);
        });
    }

    @Test
    void getPersonWithCarsSuccessful() {
        ServiceResponse<PersonResponse> response = personService.getPersonWithCars(1L);

        PersonResponse person = response.getPayload();

        assertEquals(2, person.getCars().size());
    }

    @Test
    void getPersonWithCars_NoExistingPerson() {
        assertThatExceptionOfType(PersonNotFoundException.class)
                .isThrownBy(() -> personService.getPersonWithCars(999L))
                .withMessage("Person with id: 999, not found");
    }

    @Test
    void getPersonWithCars_PersonWithNoCars() {
        PersonRequest personRequest = PersonRequest.builder()
                .name("Gulomjon")
                .birthdate(LocalDate.of(2002, 12, 30))
                .build();
        personService.createPerson(personRequest);

        ServiceResponse<PersonResponse> response = personService.getPersonWithCars(2L);
        PersonResponse person = response.getPayload();

        assertEquals(0, person.getCars().size());
    }

}
