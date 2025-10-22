package uz.mkh.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import uz.mkh.model.dto.CarDto;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class PersonResponse {
    private Long id;
    private String name;
    private LocalDate birthdate;
    private List<CarDto> cars;
}
