package avada.spacelab.kino_cms.service.admin;

import avada.spacelab.kino_cms.model.dto.admin.MovieDto;
import avada.spacelab.kino_cms.model.dto.admin.MoviesResponceDto;
import java.util.List;
import java.util.Map;

public interface MovieService {

    Map<Boolean, List<MoviesResponceDto>> getPartitionedMovies();

    List<MovieDto> getAllMovies();

    List<MovieDto> getMoviesDyIdResponceList(List<MoviesResponceDto> list);

    MovieDto getMovieById(long id);

    void save(MovieDto movieDto, String picturesJson);

}
