package uz.mkh.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uz.mkh.model.response.ServiceResponse;
import uz.mkh.model.response.StatisticResponse;
import uz.mkh.service.impl.StatisticServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StatisticsServiceWithMocksTest {

    @Mock
    private CarService carService;
    @Mock
    private PersonService personService;

    @InjectMocks
    private StatisticServiceImpl statisticServiceImpl;

    @Test
    void getStatisticsSuccessful() {
        long carCount = 10;
        long personCount = 32;
        long vendorCount = 5;
        when(carService.getAllCount()).thenReturn(carCount);
        when(personService.getAllCount()).thenReturn(personCount);
        when(carService.getVendorCount()).thenReturn(vendorCount);

        ServiceResponse<StatisticResponse> response = statisticServiceImpl.getStatistics();

        assertEquals(carCount, response.getPayload().getCarCount());
        assertEquals(personCount, response.getPayload().getPersonCount());
        assertEquals(vendorCount, response.getPayload().getUniqueVendorCount());

    }

    @Test
    void clearDatabaseSuccessful() {
        statisticServiceImpl.clearDatabase();

        verify(personService, times(1)).clearData();
    }
}
