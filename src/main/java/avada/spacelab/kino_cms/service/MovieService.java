package avada.spacelab.kino_cms.service;

import avada.spacelab.kino_cms.model.dto.MovieDto;
import avada.spacelab.kino_cms.model.dto.MoviesResponceDto;
import java.util.List;
import java.util.Map;

public interface MovieService {
    Map<Boolean, List<MoviesResponceDto>> getPartitionedMovies();

    List<MovieDto> getAllMovies();

    MovieDto getMovieById(long id);

    void save(MovieDto movieDto, String picturesJson);
}
