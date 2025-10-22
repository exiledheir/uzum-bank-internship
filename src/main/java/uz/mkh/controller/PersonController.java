package uz.mkh.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.mkh.model.dto.PersonDto;
import uz.mkh.model.request.PersonRequest;
import uz.mkh.model.response.PersonResponse;
import uz.mkh.model.response.ServiceResponse;
import uz.mkh.service.PersonService;

@Valid
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PersonController {
    private final PersonService personService;

    @PostMapping("/person")
    public ResponseEntity<Void> createPerson(@RequestBody @Valid PersonRequest request) {
        ServiceResponse<PersonDto> response = personService.createPerson(request);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @GetMapping("/personwithcars/{personId}")
    public ResponseEntity<ServiceResponse<PersonResponse>> getPersonWithCars(@PathVariable Long personId) {
        ServiceResponse<PersonResponse> response = personService.getPersonWithCars(personId);
        return ResponseEntity.ok(response);
    }
}
