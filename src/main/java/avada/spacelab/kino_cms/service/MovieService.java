package avada.spacelab.kino_cms.service;

import avada.spacelab.kino_cms.model.dto.MovieDto;
import java.util.List;
import java.util.Map;

public interface MovieService {
    Map<Boolean, List<MovieDto>> getPartitionedMovies();

    List<MovieDto> getAllMovies();

    MovieDto getMovieById(long id);

    void save(MovieDto movieDto, String picturesJson);
}
