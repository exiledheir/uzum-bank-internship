package uz.mkh.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
@Tag(name = "Statistics", description = "used to retrieve statistics")
public class StatisticsController {
    private final StatisticService statisticService;

    @Operation(summary = "Gets statistics", description = "Returns statistics")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved")
    @GetMapping("/statistics")
    public ResponseEntity<ServiceResponse<StatisticResponse>> getStatistics() {
        ServiceResponse<StatisticResponse> response = statisticService.getStatistics();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Clears data from database", description = "Returns empty body")
    @ApiResponse(responseCode = "200", description = "Successfully cleared database")
    @GetMapping("/clear")
    public ResponseEntity<Void> clearDatabase() {
        statisticService.clearDatabase();
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
