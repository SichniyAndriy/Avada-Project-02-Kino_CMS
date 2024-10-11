package avada.spacelab.kino_cms.model.mapper;

import avada.spacelab.kino_cms.model.dto.TheaterPictureDto;
import avada.spacelab.kino_cms.model.entity.TheaterPicture;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TheaterPictureMapper {
    TheaterPictureMapper INSTANCE = Mappers.getMapper(TheaterPictureMapper.class);

    TheaterPictureDto fromEntityToDto(TheaterPicture theaterPicture);
    TheaterPicture fromDtoToEntity(TheaterPictureDto theaterPictureDto);
}
