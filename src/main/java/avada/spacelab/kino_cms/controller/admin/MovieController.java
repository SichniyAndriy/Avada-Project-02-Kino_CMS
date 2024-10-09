package avada.spacelab.kino_cms.controller.admin;

import avada.spacelab.kino_cms.model.dto.MovieDto;
import avada.spacelab.kino_cms.model.entity.Movie;
import avada.spacelab.kino_cms.model.mapper.MovieMapper;
import avada.spacelab.kino_cms.service.MovieService;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(
            @Autowired MovieService movieService
    ) {
        this.movieService = movieService;
    }

    @GetMapping(path = {"","/"})
    public String films(Model model) {
        List<MovieDto> allMovies = movieService.getAllMovies();
        Map<Boolean, List<MovieDto>> collected = allMovies.stream()
                .collect(Collectors.partitioningBy(item ->
                        item.schedules().stream().anyMatch(ch ->
                Objects.equals(ch.date(), LocalDate.now()) ) )
        );
        collected.get(true).sort(Comparator.comparingLong(MovieDto::id));
        collected.get(false).sort(Comparator.comparingLong(MovieDto::id));
        model.addAttribute("currentMovies", collected.get(true));
        model.addAttribute("futureMovies", collected.get(false));
        return "admin/_2_0_movies";
    }

    @GetMapping("/show/{id}")
    public String showFilm(
            @PathVariable int id,
            Model model
    ) {
        MovieDto movieDto = (id == 0) ? MovieDto.EMPTY() : movieService.getMovieById(id);
        model.addAttribute("movieDto", movieDto);
        return "admin/_2_1_movie_page";
    }

    @PostMapping("/save")
    public String saveMovie(
            @ModelAttribute MovieDto movieDto
    ) {
        Movie movie = MovieMapper.INSTANCE.fromDtoToEntity(movieDto);
        if (movie.getId() == 0) {
            movie.setId(null);
        }
        movieService.save(movie);
        return "redirect:/admin/movies/";
    }
}
