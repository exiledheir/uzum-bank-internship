package uz.mkh.service.impl;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.mkh.exception.DataDoesntExistException;
import uz.mkh.exception.PersonAlreadyExistsException;
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
    private final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);

    @Override
    @Transactional(readOnly = true)
    public Long getAllCount() {
        logger.info("retrieving person amount");
        List<PersonEntity> personList = personRepository.findAll();
        return (long) personList.size();
    }

    @Override
    @Transactional
    public ServiceResponse<PersonDto> createPerson(@NotNull PersonRequest request) {
        logger.info("creating person entity");
        validatePersonExist(request);

        PersonEntity person = personMapper.toEntity(request);
        person = personRepository.save(person);
        PersonDto dto = personMapper.toDto(person);

        logger.info("person entity created and saved in database");
        return ServiceResponse.createSuccess(dto);
    }

    @Override
    @Transactional(readOnly = true)
    public ServiceResponse<PersonResponse> getPersonWithCars(@NotNull Long id) {
        logger.info("retrieving person information");
        PersonEntity person = validatePerson(id);

        PersonResponse response = personMapper.toResponse(person);
        List<CarDto> cars = carRepository.findCarByOwnerIdIs(id)
                .stream()
                .map(carMapper::toDto)
                .toList();
        response.setCars(cars);

        logger.info("person information retrieved");
        return ServiceResponse.createSuccess(response);
    }

    private PersonEntity validatePerson(Long id) {
        return personRepository.findById(id).orElseThrow(() -> {
            logger.error("person doesnt exists");
            return new DataDoesntExistException("Person with id: " + id + ", doesnt exists");
        });
    }

    private void validatePersonExist(PersonRequest request) {
        if (personRepository.existsByName(request.getName()) && personRepository.existsByBirthdate(request.getBirthdate())) {
            logger.error("Person with name: " + request.getName() + ", birthdate: " + request.getBirthdate() + " exists");
            throw new PersonAlreadyExistsException("Person with name: " + request.getName() + ", birthdate: " + request.getBirthdate() + " exists");
        }
    }

    @Override
    @Transactional
    public void clearData() {
        logger.warn("clearing person table in database");
        personRepository.deleteAllInBatch();
    }
}
