package uz.mkh.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceResponse<P extends Serializable> implements Serializable {
    private P payload;
    private String message;
    private boolean success;

    public static <P extends Serializable> ServiceResponse<P> createSuccess(P payload) {
        return new ServiceResponse<>(payload, "", true);
    }
}
