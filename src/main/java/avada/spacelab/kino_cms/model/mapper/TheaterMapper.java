package avada.spacelab.kino_cms.model.mapper;

import avada.spacelab.kino_cms.model.dto.TheaterDto;
import avada.spacelab.kino_cms.model.entity.Theater;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = ComponentModel.SPRING)
public interface TheaterMapper {
    TheaterMapper INSTANCE = Mappers.getMapper(TheaterMapper.class);

    TheaterDto fromEntityToDto(Theater theater);

    @Mapping(target = "pictures", ignore = true)
    @Mapping(target = "auditoriums", ignore = true)
    Theater fromDtoToEntity(TheaterDto theaterDto);
}
