package uz.mkh.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class PersonRequest {
    @NotNull(message = "person name cant be null")
    @NotBlank(message = "Person name should not be blank")
    private String name;
    @PastOrPresent(message = "Date cannot be in the future")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd.MM.yyyy")
    @NotNull(message = "birthdate cant be null")
    private LocalDate birthdate;
}
