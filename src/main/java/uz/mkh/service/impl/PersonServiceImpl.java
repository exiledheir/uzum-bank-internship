package uz.mkh.service.impl;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.mkh.exception.PersonAlreadyExistsException;
import uz.mkh.exception.PersonNotFoundException;
import uz.mkh.mapper.CarMapper;
import uz.mkh.mapper.PersonMapper;
import uz.mkh.model.dto.CarDto;
import uz.mkh.model.dto.PersonDto;
import uz.mkh.model.entity.PersonEntity;
import uz.mkh.model.request.PersonRequest;
import uz.mkh.model.response.PersonResponse;
import uz.mkh.model.response.ServiceResponse;
import uz.mkh.repository.CarRepository;
import uz.mkh.repository.PersonRepository;
import uz.mkh.service.PersonService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final CarRepository carRepository;
    private final PersonMapper personMapper;
    private final CarMapper carMapper;

    @Override
    public Long getAllCount() {
        List<PersonEntity> personList = personRepository.findAll();
        return (long) personList.size();
    }

    @Override
    @Transactional
    public ServiceResponse<PersonDto> createPerson(@NotNull PersonRequest request) {
        if (personRepository.existsById(request.getId()))
            throw new PersonAlreadyExistsException("Person with id: " + request.getId() + ", already exists");

        PersonEntity person = personMapper.toEntity(request);
        person = personRepository.save(person);
        PersonDto dto = personMapper.toDto(person);

        return ServiceResponse.createSuccess(dto);
    }

    @Override
    public ServiceResponse<PersonResponse> getPersonWithCars(@NotNull Long id) {
        PersonEntity person = personRepository.findById(id).
                orElseThrow(() -> new PersonNotFoundException("Person with id: " + id + ", not found"));

        PersonResponse response = personMapper.toResponse(person);
        List<CarDto> cars = carRepository.findCarByOwnerIdIs(id)
                .stream()
                .map(carMapper::toDto)
                .toList();
        response.setCars(cars);
        return ServiceResponse.createSuccess(response);
    }

    public void clearData() {
        personRepository.deleteAllInBatch();
    }
}
