package avada.spacelab.kino_cms.controller.admin;

import avada.spacelab.kino_cms.controller.util.ControllerUtil;
import avada.spacelab.kino_cms.model.dto.admin.MovieDto;
import avada.spacelab.kino_cms.model.dto.admin.MoviesResponceDto;
import avada.spacelab.kino_cms.service.admin.MovieService;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
        Map<Boolean, List<MoviesResponceDto>> partitioned = movieService.getPartitionedMovies();
        model.addAttribute("currentMovies", partitioned.get(true));
        model.addAttribute("futureMovies", partitioned.get(false));
        return "admin/_2_0_movies";
    }

    @GetMapping("/show/{id}")
    public String showFilm(
            @PathVariable int id,
            Model model
    ) {
        MovieDto movie = (id == 0) ? MovieDto.EMPTY() : movieService.getMovieById(id);
        model.addAttribute("movie", movie);
        return "admin/_2_1_movie_page";
    }

    @PostMapping("/save")
    public ResponseEntity<HttpStatus> saveMovie(
            @ModelAttribute MovieDto movie,
            @RequestParam String picturesJson
    ) {
        movieService.save(movie, picturesJson);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PostMapping("/save/file")
    public ResponseEntity<String> saveFile(
        @RequestParam MultipartFile file,
        @RequestParam String timestamp,
        @RequestParam String ext
    ) throws IOException {
        final String PATH_TO_MOVIES = "pictures/movies";
        final String fileName = ControllerUtil.savePictureOnServer(
                PATH_TO_MOVIES, file.getOriginalFilename(), timestamp, ext, file
        );
        return ResponseEntity.ok(fileName);
    }
}
