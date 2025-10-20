package uz.mkh.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import uz.mkh.model.dto.CarDto;
import uz.mkh.model.entity.CarEntity;
import uz.mkh.model.request.CarRequest;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CarMapper {

    @Mapping(target = "ownerId", source = "owner.id")
    CarDto toDto(CarEntity carEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    CarEntity toEntity(CarRequest request);

}
