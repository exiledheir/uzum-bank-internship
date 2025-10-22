package uz.mkh.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceResponse<P>{
    private P payload;
    private String message;
    private boolean success;

    public static <P> ServiceResponse<P> createSuccess(P payload) {
        return new ServiceResponse<>(payload, "", true);
    }
}
