package avada.spacelab.kino_cms.service.admin.impl;

import avada.spacelab.kino_cms.model.dto.admin.MovieDto;
import avada.spacelab.kino_cms.model.dto.admin.MoviePictureDto;
import avada.spacelab.kino_cms.model.dto.admin.MoviesResponceDto;
import avada.spacelab.kino_cms.model.entity.Movie;
import avada.spacelab.kino_cms.model.entity.MoviePicture;
import avada.spacelab.kino_cms.model.entity.Schedule;
import avada.spacelab.kino_cms.model.entity.SeoBlock;
import avada.spacelab.kino_cms.model.mapper.admin.MovieMapper;
import avada.spacelab.kino_cms.model.mapper.admin.MoviePictureMapper;
import avada.spacelab.kino_cms.repository.MovieRepository;
import avada.spacelab.kino_cms.service.admin.MovieService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;


    public MovieServiceImpl(
            @Autowired MovieRepository movieRepository
    ) {
        this.movieRepository = movieRepository;
    }

    /*------------------------------ Public part ------------------------------*/

    public Map<Boolean, List<MoviesResponceDto>> getPartitionedMovies() {
        List<Movie> movies = movieRepository.findAll();

        Predicate<Schedule> predicate = item -> item.getKey().getDate().isEqual(LocalDate.now());
        Map<Boolean, List<MoviesResponceDto>> partitioned = movies.stream()
                .collect(Collectors.partitioningBy(
                        movie -> movie.getSchedules().stream().anyMatch(predicate),
                        Collectors.mapping(
                                MovieMapper.INSTANCE::fromEntityToResponceDto,
                                Collectors.toList()
                        )
                ));
        partitioned.get(true).sort(Comparator.comparingLong(MoviesResponceDto::id));
        partitioned.get(false).sort(Comparator.comparingLong(MoviesResponceDto::id));
        return partitioned;
    }

    public List<MovieDto> getAllMovies() {
        Stream<Movie> movies = movieRepository.findAll().stream();
        return movies
                .sorted(Comparator.comparingLong(Movie::getId))
                .map(MovieMapper.INSTANCE::fromEntityToDto)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<MovieDto> getMoviesDyIdResponceList(List<MoviesResponceDto> list) {
        return list.stream()
                .map(mrt -> getMovieById(mrt.id()))
                .toList();
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
        if (movie.getId() != null && movie.getId() == 0) {
            movie.setId(null);
        }
        setPictures(movie, picturesJson);

        movieRepository.save(movie);
    }

    /*------------------------------ Private part ------------------------------*/

    private void setPictures(Movie movie, String picturesJson) {
        List<MoviePicture> pictures = parsePicturesJson(picturesJson);
        movie.setPictures(pictures);
        pictures.forEach(picture -> picture.setMovie(movie));
    }

    private List<MoviePicture> parsePicturesJson(String picturesJson) {
        List<MoviePictureDto> pictureDtos;
        try {
            pictureDtos = new ObjectMapper()
                    .readValue(picturesJson, new TypeReference<>() {
                    });
        } catch (JsonProcessingException | IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
        return pictureDtos.stream()
                .map(MoviePictureMapper.INSTANCE::fromDtoToEntity)
                .toList();
    }

}
