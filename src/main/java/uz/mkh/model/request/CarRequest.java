package uz.mkh.model.request;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class CarRequest {
    @NotNull(message = "model cant be null")
    @NotBlank(message = "model cant be empty or blank")
    private String model;
    @NotNull(message = "horsePower cant be null")
    @Min(value = 0)
    private Long horsePower;
    @NotNull(message = "Owner cant be null")
    private Long ownerId;
}
