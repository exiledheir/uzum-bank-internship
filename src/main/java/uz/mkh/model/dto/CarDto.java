package uz.mkh.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CarDto {
    private Long id;
    private String model;
    private Long horsePower;
    private Long ownerId;
}
