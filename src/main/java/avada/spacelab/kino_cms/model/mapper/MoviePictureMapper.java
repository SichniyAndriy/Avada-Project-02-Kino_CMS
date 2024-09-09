package avada.spacelab.kino_cms.model.mapper;

import avada.spacelab.kino_cms.model.dto.MoviePictureDto;
import avada.spacelab.kino_cms.model.entity.MoviePicture;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MoviePictureMapper {
    MoviePictureMapper INSTANCE = Mappers.getMapper(MoviePictureMapper.class);

    MoviePictureDto fromEntityToDto(MoviePicture moviePicture);
    MoviePicture fromDtoToEntity(MoviePictureDto moviePictureDto);
}
