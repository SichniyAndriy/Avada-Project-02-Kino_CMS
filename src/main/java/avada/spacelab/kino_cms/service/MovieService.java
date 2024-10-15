package avada.spacelab.kino_cms.service;

import avada.spacelab.kino_cms.model.dto.MovieDto;
import avada.spacelab.kino_cms.model.dto.MoviePictureDto;
import avada.spacelab.kino_cms.model.entity.Movie;
import avada.spacelab.kino_cms.model.entity.MoviePicture;
import avada.spacelab.kino_cms.model.entity.SeoBlock;
import avada.spacelab.kino_cms.model.mapper.MovieMapper;
import avada.spacelab.kino_cms.model.mapper.MoviePictureMapper;
import avada.spacelab.kino_cms.repository.MovieRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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
                .sorted(Comparator.comparingLong(Movie::getId))
                .map(MovieMapper.INSTANCE::fromEntityToDto)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public MovieDto getMovieById(long id) {
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        if (optionalMovie.isPresent()) {
            Movie movie = optionalMovie.get();
            if (movie.getSeoBlock() == null) {
                movie.setSeoBlock(new SeoBlock());
            }
            return MovieMapper.INSTANCE.fromEntityToDto(movie);
        }
        return MovieDto.EMPTY();
    }

    public void save(MovieDto movieDto, String picturesJson) {
        List<MoviePictureDto> pictureDtos;
        try {
            pictureDtos = new ObjectMapper()
                    .readValue(picturesJson, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        List<MoviePicture> pictures = pictureDtos.stream()
                .map(MoviePictureMapper.INSTANCE::fromDtoToEntity)
                .toList();

        Movie movie = MovieMapper.INSTANCE.fromDtoToEntity(movieDto);
        if (movie.getId() == 0) {
            movie.setId(null);
        }

        movie.setPictures(pictures);
        pictures.forEach(picture -> picture.setMovie(movie));

        movieRepository.save(movie);
    }
}
