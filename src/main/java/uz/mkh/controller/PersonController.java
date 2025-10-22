package uz.mkh.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Person Management", description = "Manages persons in database")
public class PersonController {
    private final PersonService personService;

    @Operation(summary = "Creates person entity and saves it in database", description = "Returns empty body")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created"),
            @ApiResponse(responseCode = "400", description = "Bad request, validation error")}
    )
    @PostMapping("/person")
    public ResponseEntity<Void> createPerson(@RequestBody @Valid PersonRequest request) {
        ServiceResponse<PersonDto> response = personService.createPerson(request);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @Operation(summary = "Gets all information about person", description = "Returns Response with person info and his cars")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully received"),
            @ApiResponse(responseCode = "400", description = "Bad request, validation error"),
            @ApiResponse(responseCode = "404", description = "Not found, person with id not found")}
    )
    @GetMapping("/personwithcars/{personId}")
    public ResponseEntity<ServiceResponse<PersonResponse>> getPersonWithCars(@PathVariable Long personId) {
        ServiceResponse<PersonResponse> response = personService.getPersonWithCars(personId);
        return ResponseEntity.ok(response);
    }
}
