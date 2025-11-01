package uz.mkh.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class PersonDto {
    private Long id;
    private String name;
    private LocalDate birthdate;
}
