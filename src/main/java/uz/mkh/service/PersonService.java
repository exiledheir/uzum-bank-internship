package uz.mkh.service;

import jakarta.validation.constraints.NotNull;
import uz.mkh.model.dto.PersonDto;
import uz.mkh.model.request.PersonRequest;
import uz.mkh.model.response.PersonResponse;
import uz.mkh.model.response.ServiceResponse;

public interface PersonService {
    Long getAllCount();

    ServiceResponse<PersonDto> createPerson(@NotNull PersonRequest request);

    ServiceResponse<PersonResponse> getPersonWithCars(@NotNull Long id);

    void clearData();

}
