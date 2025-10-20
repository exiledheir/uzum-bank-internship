package uz.mkh.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import uz.mkh.model.dto.PersonDto;
import uz.mkh.model.entity.PersonEntity;
import uz.mkh.model.request.PersonRequest;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PersonMapper {

    PersonDto toDto(PersonEntity person);

    @Mapping(target = "id", ignore = true)
    PersonEntity toEntity(PersonRequest request);
}
