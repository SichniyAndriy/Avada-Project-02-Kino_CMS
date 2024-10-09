package avada.spacelab.kino_cms.service;

import avada.spacelab.kino_cms.model.dto.MovieDto;
import avada.spacelab.kino_cms.model.entity.Movie;
import avada.spacelab.kino_cms.model.mapper.MovieMapper;
import avada.spacelab.kino_cms.repository.MovieRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(
            @Autowired MovieRepository movieRepository
    ) {
        this.movieRepository = movieRepository;
    }

    public List<MovieDto> getAllMovies() {
        return movieRepository.findAll().stream()
                .map(MovieMapper.INSTANCE::fromEntityToDto)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public MovieDto getMovieById(long id) {
        return MovieMapper.INSTANCE.fromEntityToDto(movieRepository.findMovieById(id));
    }

    public void save(Movie movie) {
        movieRepository.save(movie);
    }
}
