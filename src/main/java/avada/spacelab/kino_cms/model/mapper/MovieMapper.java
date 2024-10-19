package avada.spacelab.kino_cms.model.mapper;

import avada.spacelab.kino_cms.model.dto.MovieDto;
import avada.spacelab.kino_cms.model.dto.MoviesResponceDto;
import avada.spacelab.kino_cms.model.entity.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = ComponentModel.SPRING)
public interface MovieMapper {
    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    MovieDto fromEntityToDto(Movie movie);

    @Mapping(target = "pictures", ignore = true)
    @Mapping(target = "schedules", ignore = true)
    Movie fromDtoToEntity(MovieDto movieDto);

    MoviesResponceDto fromEntityToResponceDto(Movie movie);
}
