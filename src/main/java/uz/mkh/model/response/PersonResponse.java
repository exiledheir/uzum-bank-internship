package uz.mkh.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import uz.mkh.model.dto.CarDto;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class PersonResponse {
    private Long id;
    private String name;
    private LocalDate birthdate;
    private List<CarDto> cars;
}
