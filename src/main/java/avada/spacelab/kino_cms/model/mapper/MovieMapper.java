package avada.spacelab.kino_cms.model.mapper;

import avada.spacelab.kino_cms.model.dto.MovieDto;
import avada.spacelab.kino_cms.model.entity.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MovieMapper {
    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    MovieDto fromEntityToDto(Movie movie);
    Movie fromDtoToEntity(MovieDto movieDto);
}
