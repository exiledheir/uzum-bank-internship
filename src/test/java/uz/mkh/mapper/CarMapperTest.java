package uz.mkh.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import uz.mkh.model.dto.CarDto;
import uz.mkh.model.entity.CarEntity;
import uz.mkh.model.entity.PersonEntity;
import uz.mkh.model.request.CarRequest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarMapperTest {

    private final CarMapper carMapper = Mappers.getMapper(CarMapper.class);

    @Test
    void fromRequestToEntitySuccess() {
        CarRequest carRequest = CarRequest.builder()
                .model("Kia-K5")
                .horsePower(399L)
                .ownerId(1L)
                .build();
        CarEntity entity = carMapper.toEntity(carRequest);

        assertEquals(carRequest.getModel(), entity.getModel());
        assertEquals(carRequest.getHorsePower(), entity.getHorsePower());
    }

    @Test
    void fromDtoToEntitySuccess() {
        PersonEntity personEntity = PersonEntity.builder()
                .id(1L)
                .name("Mukhammadjon")
                .birthdate(LocalDate.of(2002, 12, 30))
                .build();
        CarEntity carEntity = CarEntity.builder()
                .id(1L)
                .model("Kia-K5")
                .horsePower(399L)
                .owner(personEntity)
                .build();

        CarDto dto = carMapper.toDto(carEntity);

        assertEquals(carEntity.getModel(), dto.getModel());
        assertEquals(carEntity.getHorsePower(), dto.getHorsePower());
        assertEquals(personEntity.getId(), dto.getOwnerId());
    }
}