package avada.spacelab.kino_cms.model.mapper.admin;

import avada.spacelab.kino_cms.model.dto.admin.MoviePictureDto;
import avada.spacelab.kino_cms.model.entity.MoviePicture;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = ComponentModel.SPRING)
public interface MoviePictureMapper {

    MoviePictureMapper INSTANCE = Mappers.getMapper(MoviePictureMapper.class);


    MoviePictureDto fromEntityToDto(MoviePicture moviePicture);

    @Mapping(target = "movie", ignore = true)
    MoviePicture fromDtoToEntity(MoviePictureDto moviePictureDto);

}
