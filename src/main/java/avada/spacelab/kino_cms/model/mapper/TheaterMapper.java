package avada.spacelab.kino_cms.model.mapper;

import avada.spacelab.kino_cms.model.dto.TheaterDto;
import avada.spacelab.kino_cms.model.entity.Theater;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TheaterMapper {
    TheaterMapper INSTANCE = Mappers.getMapper(TheaterMapper.class);

    TheaterDto fromEntityToDto(Theater theater);
    Theater fromDtoToEntity(TheaterDto theaterDto);
}
