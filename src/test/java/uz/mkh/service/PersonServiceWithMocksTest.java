package uz.mkh.service;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uz.mkh.exception.DataDoesntExistException;
import uz.mkh.exception.PersonAlreadyExistsException;
import uz.mkh.mapper.CarMapper;
import uz.mkh.mapper.PersonMapper;
import uz.mkh.model.dto.PersonDto;
import uz.mkh.model.entity.CarEntity;
import uz.mkh.model.entity.PersonEntity;
import uz.mkh.model.request.PersonRequest;
import uz.mkh.model.response.PersonResponse;
import uz.mkh.model.response.ServiceResponse;
import uz.mkh.repository.CarRepository;
import uz.mkh.repository.PersonRepository;
import uz.mkh.service.impl.PersonServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class PersonServiceWithMocksTest {

    @Mock
    private PersonRepository personRepository;
    @Mock
    private CarRepository carRepository;
    @Mock
    private CarMapper carMapper;
    @Mock
    private PersonMapper personMapper;

    @InjectMocks
    private PersonServiceImpl personServiceImpl;

    private PersonRequest personRequest;
    private PersonDto personDto;
    private PersonEntity personEntity;
    private PersonResponse personResponse;
    private List<CarEntity> cars;

    private Validator validator;
    private Set<ConstraintViolation<PersonRequest>> validate;

    @BeforeEach
    void setUp() {
        personRequest = PersonRequest.builder()
                .name("Mukhammadjon")
                .birthdate(LocalDate.of(2002, 12, 30))
                .build();
        personDto = PersonDto.builder()
                .id(1L)
                .name("Mukhammadjon")
                .birthdate(LocalDate.of(2002, 12, 30))
                .build();
        personEntity = PersonEntity.builder()
                .id(1L)
                .name("Mukhammadjon")
                .birthdate(LocalDate.of(2002, 12, 30))
                .build();
        personResponse = PersonResponse.builder()
                .name("Mukhammadjon")
                .birthdate(LocalDate.of(2002, 12, 30))
                .build();
        cars = List.of(
                CarEntity.builder().id(1L)
                        .model("Kia-K5")
                        .horsePower(399L)
                        .owner(personEntity)
                        .build(),
                CarEntity.builder().id(1L)
                        .model("BMW-X5")
                        .horsePower(399L)
                        .owner(personEntity)
                        .build(),
                CarEntity.builder().id(1L)
                        .model("Mercedes-M5")
                        .horsePower(399L)
                        .owner(personEntity)
                        .build());

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void createPersonSuccessful() {
        when(personRepository.existsByNameAndBirthdate(personRequest.getName(),
                personRequest.getBirthdate())).thenReturn(false);
        when(personMapper.toEntity(personRequest)).thenReturn(personEntity);
        when(personRepository.save(personEntity)).thenReturn(personEntity);
        when(personMapper.toDto(personEntity)).thenReturn(personDto);

        ServiceResponse<PersonDto> response = personServiceImpl.createPerson(personRequest);

        assertEquals(personRequest.getName(), response.getPayload().getName());
        assertEquals(personRequest.getBirthdate(), response.getPayload().getBirthdate());
    }

    @Test
    void createExistingPerson() {
        PersonRequest personRequest = PersonRequest.builder()
                .name("Gulomjon")
                .birthdate(LocalDate.of(2023, 12, 30))
                .build();

        when(personRepository.existsByNameAndBirthdate(personRequest.getName(),
                personRequest.getBirthdate()
        )).thenReturn(true);

        assertThatExceptionOfType(PersonAlreadyExistsException.class)
                .isThrownBy(() -> personServiceImpl.createPerson(personRequest));
    }

    @Test
    void getPersonWithCarsSuccessful() {
        when(personRepository.findById(1L)).thenReturn(Optional.of(personEntity));
        when(personMapper.toResponse(personEntity)).thenReturn(personResponse);
        when(carRepository.findCarByOwnerIdIs(1L)).thenReturn(cars);

        ServiceResponse<PersonResponse> response = personServiceImpl.getPersonWithCars(1L);

        assertEquals(personResponse.getName(), response.getPayload().getName());
        assertEquals(personResponse.getBirthdate(), response.getPayload().getBirthdate());
        assertEquals(cars.size(), response.getPayload().getCars().size());
    }

    @Test
    void getPersonWithCarsNotExistingPerson() {
        when(personRepository.findById(100L)).thenReturn(Optional.empty());

        assertThrows(DataDoesntExistException.class, () -> personServiceImpl.getPersonWithCars(100L));
    }

    @Test
    void getPersonWithoutCars() {
        when(personRepository.findById(1L)).thenReturn(Optional.of(personEntity));
        when(personMapper.toResponse(personEntity)).thenReturn(personResponse);
        when(carRepository.findCarByOwnerIdIs(1L)).thenReturn(List.of());

        ServiceResponse<PersonResponse> response = personServiceImpl.getPersonWithCars(1L);

        assertEquals(personResponse.getName(), response.getPayload().getName());
        assertEquals(personResponse.getBirthdate(), response.getPayload().getBirthdate());
        assertEquals(0, response.getPayload().getCars().size());
    }

    @Test
    void createPersonWithNotValidName() {
        PersonRequest personRequest = PersonRequest.builder()
                .name(null)
                .birthdate(LocalDate.of(2023, 12, 30))
                .build();
        validate = validator.validate(personRequest);
        assertFalse(validate.isEmpty());
    }

    @Test
    void createPersonWithNotValidBirthdate() {
        PersonRequest personRequest1 = PersonRequest.builder()
                .name("null")
                .birthdate(LocalDate.of(2040, 12, 30))
                .build();
        validate = validator.validate(personRequest1);
        assertFalse(validate.isEmpty());
    }

    @Test
    void getAllPeopleCountSuccess() {
        List<PersonEntity> storedPeople = List.of(personEntity);
        when(personRepository.findAll()).thenReturn(storedPeople);
        long response = personServiceImpl.getAllCount();
        assertEquals(storedPeople.size(), response);
    }

    @Test
    void clearDataSuccess() {
        personServiceImpl.clearData();

        verify(personRepository, times(1)).deleteAllInBatch();
    }
}
