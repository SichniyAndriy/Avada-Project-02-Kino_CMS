package avada.spacelab.kino_cms.service;

import avada.spacelab.kino_cms.model.dto.MovieDto;
import avada.spacelab.kino_cms.model.dto.MoviesResponceDto;
import avada.spacelab.kino_cms.model.dto.SeoBlockDto;
import avada.spacelab.kino_cms.model.entity.Movie;
import avada.spacelab.kino_cms.model.entity.MovieDetails;
import avada.spacelab.kino_cms.model.entity.Schedule;
import avada.spacelab.kino_cms.model.entity.SeoBlock;
import avada.spacelab.kino_cms.repository.MovieRepository;
import avada.spacelab.kino_cms.repository.ScheduleRepository;
import avada.spacelab.kino_cms.service.impl.MovieServiceImpl;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MovieServiceTest {
    private AutoCloseable openedMocks;
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private ScheduleRepository scheduleRepository;
    @InjectMocks
    private MovieServiceImpl movieService;

    private final int MOVIES_AMOUNT = 8;
    private final int SCHEDULES_IN_MOVIE = 4;
    private final int ZERO_SIZE = 0;
    private final long FIRST_ID = 1;

    @BeforeEach
    void setUp() {
        openedMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openedMocks.close();
    }

    @Test
    @DisplayName("Test getPartitionedMovies() with with valid list")
    void test_getPartitionedMovies_withValidList() {
        List<Movie> movies = getMovies();
        when(movieRepository.findAll()).thenReturn(movies);
        Map<Boolean, List<MoviesResponceDto>> partitionedMovies = movieService.getPartitionedMovies();
        assertEquals(2,partitionedMovies.size());
        assertEquals(MOVIES_AMOUNT / 2, partitionedMovies.get(true).size());
        assertEquals(MOVIES_AMOUNT / 2, partitionedMovies.get(false).size());
        verify(movieRepository).findAll();
    }

    @Test
    @DisplayName("Test getPartitionedMovies() with with empty list")
    void test_getPartitionedMovies_withEmptyList() {
        List<Movie> movies = getMovies();
        when(movieRepository.findAll()).thenReturn(Collections.emptyList());
        Map<Boolean, List<MoviesResponceDto>> partitionedMovies = movieService.getPartitionedMovies();
        assertEquals(2,partitionedMovies.size());
        assertEquals(ZERO_SIZE, partitionedMovies.get(true).size());
        assertEquals(ZERO_SIZE, partitionedMovies.get(false).size());
        verify(movieRepository).findAll();
    }

    @Test
    @DisplayName("Test getPartitionedMovies() with with null list")
    void test_getPartitionedMovies_withNullList() {
        when(movieRepository.findAll()).thenReturn(null);

        assertThrows(NullPointerException.class,() -> movieService.getPartitionedMovies());
        verify(movieRepository).findAll();
    }

    @Test
    @DisplayName("Test getAllMovies() with valid list")
    void test_getAllMovies_withValidList() {
        List<Movie> movies = getMovies();

        when(movieRepository.findAll()).thenReturn(movies);
        List<MovieDto> result = movieService.getAllMovies();

        assertEquals(MOVIES_AMOUNT, result.size());
        verify(movieRepository).findAll();
    }

    @Test
    @DisplayName("Test getAllMovies() with empty list")
    void test_getAllMovies_withEmptyList() {
        when(movieRepository.findAll()).thenReturn(Collections.emptyList());
        List<MovieDto> result = movieService.getAllMovies();

        assertEquals(ZERO_SIZE, result.size());
        verify(movieRepository).findAll();
    }

    @Test
    @DisplayName("Test getAllMovies() with null list")
    void test_getAllMovies_withNullList() {
        when(movieRepository.findAll()).thenReturn(null);
        List<MovieDto> result = movieService.getAllMovies();
        assertEquals(ZERO_SIZE,  result.size());
        verify(movieRepository).findAll();
    }

    @Test
    @DisplayName("Test getMovieById() with valid movie and SeoBlock")
    void test_getMovieById_withValidMovieAndSeoBlock() {
        Movie movie = new Movie();
        movie.setId(FIRST_ID);
        movie.setSeoBlock(new SeoBlock());
        when(movieRepository.findById(anyLong())).thenReturn(Optional.of(movie));

        MovieDto result = movieService.getMovieById(FIRST_ID);

        assertEquals(FIRST_ID, result.id());
        verify(movieRepository).findById(anyLong());
    }

    @Test
    @DisplayName("Test getMovieById() with valid movie and null SeoBlock")
    void test_getMovieById_withValidMovieAndNullSeoBlock() {
        Movie movie = new Movie();
        movie.setId(FIRST_ID);
        when(movieRepository.findById(anyLong())).thenReturn(Optional.ofNullable(movie));

        MovieDto result = movieService.getMovieById(FIRST_ID);

        assertEquals(FIRST_ID, result.id());
        assertNotNull(result.seoBlock());
        verify(movieRepository).findById(anyLong());
    }

    @Test
    @DisplayName("Test getMovieById() with null movie")
    void test_getMovieById_withNullMovie() {
        when(movieRepository.findById(anyLong())).thenReturn(Optional.empty());

        MovieDto result = movieService.getMovieById(FIRST_ID);
        assertAll(
                () -> assertNull(result.id()),
            () -> assertNull(result.uaTitle()),
            () -> assertNull(result.nativeTitle()),
            () -> assertNull(result.description()),
            () -> assertNull(result.ageCenz()),
            () -> assertNull(result.has2D()),
            () -> assertNull(result.has3D()),
            () -> assertNull(result.hasImax())
        );
        verify(movieRepository).findById(anyLong());
    }

    @Test
    @DisplayName("Test save() with valid movie and valid json")
    void test_save_withValidMovieAndValidJson() {
        MovieDto movieDto = getMovieDto();
        when(movieRepository.save(any(Movie.class)))
                .thenReturn(new Movie());

        movieService.save(movieDto, "[]");

        verify(movieRepository).save(any(Movie.class));
    }

    @Test
    @DisplayName("Test save() with valid movieDto and invalid json")
    void test_save_withValidMovieDtoAndInvalidJson() {
        MovieDto movieDto = getMovieDto();
        when(movieRepository.save(any(Movie.class)))
                .thenReturn(new Movie());

        assertThrows(
                RuntimeException.class,
                () -> movieService.save(movieDto, "0000")
        );
        verify(movieRepository, never()).save(any(Movie.class));
    }

    @Test
    @DisplayName("Test save() with valid movieDto and null json")
    void test_save_withValidMovieDtoAndNullJson() {
        MovieDto movieDto = getMovieDto();
        when(movieRepository.save(any(Movie.class)))
                .thenReturn(new Movie());

        assertThrows(
                RuntimeException.class,
                () -> movieService.save(movieDto, null)
        );
        verify(movieRepository, never()).save(any(Movie.class));
    }

    @Test
    @DisplayName("Test save() with null MovieDto")
    void test_save_withNullMovieDto() {
        when(movieRepository.save(any(Movie.class)))
                .thenReturn(new Movie());

        assertThrows(
                NullPointerException.class,
                () -> movieService.save(null, "[]")
            );
        verify(movieRepository, never()).save(any(Movie.class));
    }

    private List<Movie> getMovies() {
        List<Movie> movies = new ArrayList<>();
        for (long i = 1; i <= MOVIES_AMOUNT; ++i) {
            Movie movie = new Movie();
            movie.setId(i);
            List<Schedule> schedules = new ArrayList<>();
            LocalDate date = (i % 2 == 1) ? LocalDate.now() : LocalDate.now().plusDays(5);
            for (int j = 1; j <= SCHEDULES_IN_MOVIE; j++) {
                schedules.add(new Schedule(null,null,date, null));
            }
            movie.setSchedules(schedules);
            movies.add(movie);
        }
        return movies;
    }

    private MovieDto getMovieDto() {
        return new MovieDto(
                FIRST_ID,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                MovieDetails.EMPTY(),
                SeoBlockDto.EMPTY(),
                null
        );
    }

}
