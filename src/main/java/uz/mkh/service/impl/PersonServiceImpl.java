package uz.mkh.service.impl;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.mkh.exception.CarAlreadyExistsException;
import uz.mkh.exception.PersonAlreadyExistsException;
import uz.mkh.mapper.PersonMapper;
import uz.mkh.model.dto.PersonDto;
import uz.mkh.model.entity.PersonEntity;
import uz.mkh.model.request.PersonRequest;
import uz.mkh.model.response.ServiceResponse;
import uz.mkh.repository.PersonRepository;
import uz.mkh.service.PersonService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository repository;
    private final PersonMapper mapper;

    @Override
    public Long getAllCount() {
        List<PersonEntity> personList = repository.findAll();
        return (long) personList.size();
    }

    @Override
    @Transactional
    public ServiceResponse<PersonDto> createPerson(@NotNull PersonRequest request) {
        if (repository.existsById(request.getId()))
            throw new PersonAlreadyExistsException("Person with id: " + request.getId() + ", already exists");

        PersonEntity person = mapper.toEntity(request);
        person = repository.save(person);
        PersonDto dto = mapper.toDto(person);

        return ServiceResponse.createSuccess(dto);
    }
}
