package avada.spacelab.kino_cms.service.impl;

import avada.spacelab.kino_cms.model.dto.MovieDto;
import avada.spacelab.kino_cms.model.dto.MoviePictureDto;
import avada.spacelab.kino_cms.model.dto.ScheduleDto;
import avada.spacelab.kino_cms.model.entity.Movie;
import avada.spacelab.kino_cms.model.entity.MoviePicture;
import avada.spacelab.kino_cms.model.entity.SeoBlock;
import avada.spacelab.kino_cms.model.mapper.MovieMapper;
import avada.spacelab.kino_cms.model.mapper.MoviePictureMapper;
import avada.spacelab.kino_cms.repository.MovieRepository;
import avada.spacelab.kino_cms.repository.ScheduleRepository;
import avada.spacelab.kino_cms.service.MovieService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final ScheduleRepository scheduleRepository;

    public MovieServiceImpl(
            MovieRepository movieRepository,
            ScheduleRepository scheduleRepository
    ) {
        this.movieRepository = movieRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public Map<Boolean, List<MovieDto>> getPartitionedMovies() {
        List<Movie> movies = movieRepository.findAll();

        List<MovieDto> movieDtos = movies.stream()
                .filter(Objects::nonNull)
                .map(movie -> {
                    if (movie.getSeoBlock() == null) {
                        movie.setSeoBlock(new SeoBlock());
                    }
                    return MovieMapper.INSTANCE.fromEntityToDto(movie);
                })
                .toList();

        Predicate<ScheduleDto> predicate = item -> item.date().isEqual((ChronoLocalDate) LocalDate.now());
        Map<Boolean, List<MovieDto>> partitioned = movieDtos.stream()
                .collect(Collectors.partitioningBy(movie ->
                        movie.schedules().stream().anyMatch(predicate)
                ));

        return partitioned;
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
        Movie movie = MovieMapper.INSTANCE.fromDtoToEntity(movieDto);
        List<MoviePicture> pictures = parsePicturesJson(picturesJson);
        movie.setPictures(pictures);
        pictures.forEach(picture -> picture.setMovie(movie));
        movieRepository.save(movie);
    }

    private List<MoviePicture> parsePicturesJson(String picturesJson) {
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
        return pictures;
    }
}
