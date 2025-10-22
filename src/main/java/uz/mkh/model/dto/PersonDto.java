package uz.mkh.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PersonDto {
    private Long id;
    private String name;
    private LocalDate birthdate;
}
