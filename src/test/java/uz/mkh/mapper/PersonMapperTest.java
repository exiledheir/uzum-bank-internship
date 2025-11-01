package uz.mkh.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import uz.mkh.model.dto.PersonDto;
import uz.mkh.model.entity.PersonEntity;
import uz.mkh.model.request.PersonRequest;
import uz.mkh.model.response.PersonResponse;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PersonMapperTest {

    private final PersonMapper personMapper = Mappers.getMapper(PersonMapper.class);

    @Test
    void fromRequestToEntitySuccess() {
        PersonRequest personRequest = PersonRequest.builder()
                .name("Mukhammadjon")
                .birthdate(LocalDate.of(2002, 12, 30))
                .build();
        PersonEntity personEntity = personMapper.toEntity(personRequest);

        assertEquals(personRequest.getName(), personEntity.getName());
        assertEquals(personRequest.getBirthdate(), personEntity.getBirthdate());
    }

    @Test
    void fromEntityToDto() {
        PersonEntity personEntity = PersonEntity.builder()
                .id(1L)
                .name("Mukhammadjon")
                .birthdate(LocalDate.of(2002, 12, 30))
                .build();
        PersonDto dto = personMapper.toDto(personEntity);

        assertNotNull(dto);
        assertEquals(personEntity.getId(), dto.getId());
        assertEquals(personEntity.getName(), dto.getName());
        assertEquals(personEntity.getBirthdate(), dto.getBirthdate());
    }

    @Test
    void fromEntityToResponse() {
        PersonEntity personEntity = PersonEntity.builder()
                .id(1L)
                .name("Mukhammadjon")
                .birthdate(LocalDate.of(2002, 12, 30))
                .build();
        PersonResponse response = personMapper.toResponse(personEntity);

        assertNotNull(response);
        assertEquals(personEntity.getName(), response.getName());
        assertEquals(personEntity.getBirthdate(), response.getBirthdate());
    }
}