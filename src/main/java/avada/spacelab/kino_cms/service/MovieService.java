package avada.spacelab.kino_cms.service;

import avada.spacelab.kino_cms.model.dto.MovieDto;
import avada.spacelab.kino_cms.model.entity.Movie;
import avada.spacelab.kino_cms.model.entity.MoviePicture;
import avada.spacelab.kino_cms.model.entity.SeoBlock;
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
        Movie movie = movieRepository.findMovieById(id);
        if (movie.getSeoBlock() == null) {
            movie.setSeoBlock(new SeoBlock());
        }
        return MovieMapper.INSTANCE.fromEntityToDto(movie);
    }

    public void save(MovieDto movieDto) {
        Movie movie = MovieMapper.INSTANCE.fromDtoToEntity(movieDto);
        if (movie.getId() == 0) {
            movie.setId(null);
        }

        List<MoviePicture> pictures = movie.getPictures();
        if (pictures == null) {
            movie.setPictures(new ArrayList<>());
        } else {
            pictures.forEach(moviePicture -> moviePicture.setMovie(movie));
        }
        movieRepository.save(movie);
    }
}
