package uz.mkh.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class StatisticResponse {
    private Long personCount;
    private Long carCount;
    private Long uniqueVendorCount;
}
