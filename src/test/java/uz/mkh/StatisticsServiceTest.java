package uz.mkh;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import uz.mkh.model.request.CarRequest;
import uz.mkh.model.request.PersonRequest;
import uz.mkh.model.response.ServiceResponse;
import uz.mkh.model.response.StatisticResponse;
import uz.mkh.service.CarService;
import uz.mkh.service.PersonService;
import uz.mkh.service.StatisticService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class StatisticsServiceTest {

    @Autowired
    private StatisticService statisticService;

    @Autowired
    private PersonService personService;

    @Autowired
    private CarService carService;

    @BeforeEach
    void setUp() {
        PersonRequest person1 = PersonRequest.builder()
                .id(1L)
                .name("Mukhammadjon")
                .birthdate(LocalDate.of(2002, 12, 30))
                .build();

        PersonRequest person2 = PersonRequest.builder()
                .id(2L)
                .name("Sardor")
                .birthdate(LocalDate.of(2002, 12, 30))
                .build();

        personService.createPerson(person1);
        personService.createPerson(person2);

        CarRequest car1 = CarRequest.builder()
                .id(1L)
                .model("BMW-X5")
                .horsePower(300L)
                .ownerId(1L)
                .build();

        CarRequest car2 = CarRequest.builder()
                .id(2L)
                .model("BMW-X3")
                .horsePower(300L)
                .ownerId(1L)
                .build();

        CarRequest car3 = CarRequest.builder()
                .id(3L)
                .model("Kia-K5")
                .horsePower(300L)
                .ownerId(2L)
                .build();

        CarRequest car4 = CarRequest.builder()
                .id(4L)
                .model("Kia-K3")
                .horsePower(300L)
                .ownerId(2L)
                .build();

        carService.createCar(car1);
        carService.createCar(car2);
        carService.createCar(car3);
        carService.createCar(car4);
    }

    @Test
    void getStatisticsSuccessful() {
        ServiceResponse<StatisticResponse> response = statisticService.getStatistics();

        StatisticResponse stats = response.getPayload();
        assertEquals(4L, stats.getCarCount());
        assertEquals(2L, stats.getPersonCount());
        assertEquals(2L, stats.getUniqueVendorCount());
    }

    @Test
    void getStatistics_EmptyDatabase() {
        statisticService.clearDatabase();

        ServiceResponse<StatisticResponse> response = statisticService.getStatistics();

        StatisticResponse stats = response.getPayload();
        assertEquals(0L, stats.getCarCount());
        assertEquals(0L, stats.getPersonCount());
        assertEquals(0L, stats.getUniqueVendorCount());
    }

    @Test
    void clearDatabaseSuccessful() {
        assertEquals(4L, carService.getAllCount());
        assertEquals(2L, personService.getAllCount());

        statisticService.clearDatabase();

        ServiceResponse<StatisticResponse> response = statisticService.getStatistics();
        StatisticResponse stats = response.getPayload();
        assertEquals(0L, stats.getCarCount());
        assertEquals(0L, stats.getPersonCount());
    }

}