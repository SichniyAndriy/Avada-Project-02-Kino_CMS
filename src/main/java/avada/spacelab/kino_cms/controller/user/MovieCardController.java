package avada.spacelab.kino_cms.controller.user;

import avada.spacelab.kino_cms.model.dto.admin.MovieDto;
import avada.spacelab.kino_cms.service.admin.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/movie-card")
public class MovieCardController {

    private final MovieService movieService;


    public MovieCardController(
            @Autowired MovieService movieService
    ) {
        this.movieService = movieService;
    }

    @GetMapping("/{id}")
    public String showFilmCard(
            @PathVariable int id,
            Model model
    ) {
        MovieDto movieById = movieService.getMovieById(id);
        model.addAttribute("movie", movieById);
        return "public/_1_1_movie_card";
    }

}
