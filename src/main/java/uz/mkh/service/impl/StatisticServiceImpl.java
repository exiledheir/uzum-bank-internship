package uz.mkh.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.mkh.model.response.ServiceResponse;
import uz.mkh.model.response.StatisticResponse;
import uz.mkh.service.CarService;
import uz.mkh.service.PersonService;
import uz.mkh.service.StatisticService;

@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {
    private final CarService carService;
    private final PersonService personService;
    private final Logger logger = LoggerFactory.getLogger(StatisticServiceImpl.class);

    @Override
    @Transactional(readOnly = true)
    public ServiceResponse<StatisticResponse> getStatistics() {
        logger.info("retrieving statistics");
        Long carCount = carService.getAllCount();
        Long personCount = personService.getAllCount();
        Long vendorCount = carService.getVendorCount();
        StatisticResponse response = StatisticResponse.builder()
                .carCount(carCount)
                .personCount(personCount)
                .uniqueVendorCount(vendorCount)
                .build();
        logger.info("statistics retrieved");

        return ServiceResponse.createSuccess(response);
    }

    @Override
    @Transactional
    public void clearDatabase() {
        logger.warn("clearing tables in database");
        personService.clearData();
    }
}
