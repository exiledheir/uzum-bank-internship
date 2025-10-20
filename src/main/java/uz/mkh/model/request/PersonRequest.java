package uz.mkh.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class PersonRequest {
    @NotNull(message = "person name cant be null")
    @NotBlank(message = "Person name should not be blank")
    private String name;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "MM/dd/yyyy")
    @NotNull(message = "birthdate cant be null")
    private LocalDate birthdate;
}
