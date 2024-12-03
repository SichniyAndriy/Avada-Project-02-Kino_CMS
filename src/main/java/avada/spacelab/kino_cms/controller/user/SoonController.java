package avada.spacelab.kino_cms.controller.user;

import avada.spacelab.kino_cms.model.dto.admin.MovieDto;
import avada.spacelab.kino_cms.model.dto.admin.MoviesResponceDto;
import avada.spacelab.kino_cms.service.admin.MovieService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/soon")
public class SoonController {
    private final MovieService movieService;


    public SoonController(
            @Autowired MovieService movieService
    ) {
        this.movieService = movieService;
    }

    @GetMapping
    public String soon(Model model) {
        List<MoviesResponceDto> moviesResponceDtos = movieService.getPartitionedMovies().get(false);
        List<MovieDto> dtoList = movieService.getMoviesDyIdResponceList(moviesResponceDtos);
        model.addAttribute("movies", dtoList);
        return "public/_2_0_soon";
    }
}
