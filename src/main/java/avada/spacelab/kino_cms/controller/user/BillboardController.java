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
@RequestMapping("/billboard")
public class BillboardController {
    private final MovieService movieService;


    public BillboardController(
            @Autowired MovieService movieService
    ) {
        this.movieService = movieService;
    }

    @GetMapping
    public String getBillboard(Model model) {
        List<MoviesResponceDto> movies = movieService.getPartitionedMovies().get(true);
        List<MovieDto> dtoList = movieService.getMoviesDyIdResponceList(movies);
        model.addAttribute("movies", dtoList);
        return "public/_1_0_billboard";
    }

}
