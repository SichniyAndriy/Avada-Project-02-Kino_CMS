package avada.spacelab.kino_cms.controller.admin;

import avada.spacelab.kino_cms.model.entity.Movie;
import avada.spacelab.kino_cms.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/movies")
public class MovieController {
    private MovieService movieService;

    public MovieController(
            @Autowired MovieService movieService
    ) {
        this.movieService = movieService;
    }

    @GetMapping(path = {"","/"})
    public String films() {
        return "admin/_2_0_movies";
    }

    @GetMapping("/show/{id}")
    public String showFilm(
            @PathVariable int id,
            Model model
    ) {
        Movie movie = (id == 0) ? new Movie() : movieService.getMovieById(id);
        model.addAttribute("movie", movie);
        return "admin/_2_1_movie_page";
    }
}
