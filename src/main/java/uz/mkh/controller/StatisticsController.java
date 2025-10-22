package uz.mkh.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.mkh.model.response.ServiceResponse;
import uz.mkh.model.response.StatisticResponse;
import uz.mkh.service.StatisticService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class StatisticsController {
    private final StatisticService statisticService;

    @GetMapping("/statistics")
    public ResponseEntity<ServiceResponse<StatisticResponse>> getStatistics() {
        ServiceResponse<StatisticResponse> response = statisticService.getStatistics();
        return ResponseEntity.ok(response);
    }
}
