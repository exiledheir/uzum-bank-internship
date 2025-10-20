package uz.mkh.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.mkh.model.dto.PersonDto;
import uz.mkh.model.request.PersonRequest;
import uz.mkh.model.response.ServiceResponse;
import uz.mkh.service.PersonService;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PersonController {
    private final PersonService personService;

    @PostMapping("/person")
    public ResponseEntity<Void> createPerson(@RequestBody @Validated PersonRequest request) {
        ServiceResponse<PersonDto> response = personService.createPerson(request);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

//    @GetMapping("/getAll")
//    public ResponseEntity<Long> getPersonCount() {
//        Long response = personService.getAllCount();
//        return ResponseEntity.ok(response);
//    }
}
