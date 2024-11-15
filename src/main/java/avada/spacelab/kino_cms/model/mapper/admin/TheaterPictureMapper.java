package avada.spacelab.kino_cms.model.mapper.admin;

import avada.spacelab.kino_cms.model.dto.admin.TheaterPictureDto;
import avada.spacelab.kino_cms.model.entity.TheaterPicture;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = ComponentModel.SPRING)
public interface TheaterPictureMapper {
    TheaterPictureMapper INSTANCE = Mappers.getMapper(TheaterPictureMapper.class);


    TheaterPictureDto fromEntityToDto(TheaterPicture theaterPicture);

    @Mapping(target = "theater", ignore = true)
    TheaterPicture fromDtoToEntity(TheaterPictureDto theaterPictureDto);
}
