package uz.mkh.service;

import uz.mkh.model.response.ServiceResponse;
import uz.mkh.model.response.StatisticResponse;

public interface StatisticService {
    ServiceResponse<StatisticResponse> getStatistics();

    void clearDatabase();
}
