package avada.spacelab.kino_cms.controller.admin;

import avada.spacelab.kino_cms.model.entity.Theater;
import avada.spacelab.kino_cms.service.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/theaters")
public class TheaterController {
    TheaterService theaterService;

    public TheaterController(
            @Autowired TheaterService theaterService
    ) {
        this.theaterService = theaterService;
    }

    @GetMapping(path = {"", "/"})
    public String theaters(Model model) {
        return "admin/_3_0_theaters";
    }

    @GetMapping("/show/{id}")
    public String showTheater(
            @PathVariable long id,
            Model model
    ) {
        Theater theater = new Theater();
        theater.setId(id);
        model.addAttribute("theater", theater);
        return "admin/_3_1_theater_page";
    }
}
