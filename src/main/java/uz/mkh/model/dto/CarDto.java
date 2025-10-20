package uz.mkh.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class CarDto {
    private Long id;
    private String model;
    private Long horsePower;
    private Long ownerId;
}
