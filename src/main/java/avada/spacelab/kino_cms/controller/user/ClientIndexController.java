package avada.spacelab.kino_cms.controller.user;

import avada.spacelab.kino_cms.model.dto.admin.MoviesResponceDto;
import avada.spacelab.kino_cms.model.entity.MainPageBanner;
import avada.spacelab.kino_cms.model.entity.MainPageBanner.Replacement;
import avada.spacelab.kino_cms.service.admin.MainPageService;
import avada.spacelab.kino_cms.service.admin.MovieService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = {"", "/", "/index"})
public class ClientIndexController {

    private final MovieService movieService;
    private final MainPageService mainPageService;

    public ClientIndexController(
            @Autowired MovieService movieService,
            @Autowired MainPageService mainPageService
    ) {
        this.movieService = movieService;
        this.mainPageService = mainPageService;
    }

    @GetMapping
    public String index(Model model) {
        Map<Boolean, List<MoviesResponceDto>> partitionedMovies = movieService.getPartitionedMovies();
        List<MainPageBanner> middleList = mainPageService.getAllByReplacement(Replacement.SLASH_BANNER);
        String middle = !middleList.isEmpty() ? middleList.getFirst().getPath() : null;
        List<MainPageBanner> banners = mainPageService.getAllByReplacement(Replacement.UP_BANNER);
        List<MainPageBanner> promotions = mainPageService.getAllByReplacement(Replacement.BOTTOM_PROMOTION);

        model.addAttribute("today", partitionedMovies.get(true));
        model.addAttribute("soon", partitionedMovies.get(false));
        model.addAttribute("banners", banners);
        model.addAttribute("middle", middle);
        model.addAttribute("promotions", promotions);
        
        return "public/index";
    }

}
