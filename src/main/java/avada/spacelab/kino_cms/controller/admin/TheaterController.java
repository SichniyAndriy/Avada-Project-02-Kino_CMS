package avada.spacelab.kino_cms.controller.admin;

import avada.spacelab.kino_cms.model.dto.AuditoriumDto;
import avada.spacelab.kino_cms.model.dto.TheaterDto;
import avada.spacelab.kino_cms.service.AuditoriumService;
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
    private TheaterService theaterService;
    private AuditoriumService auditoriumService;

    public TheaterController(
            @Autowired TheaterService theaterService,
            @Autowired AuditoriumService auditoriumService
    ) {
        this.theaterService = theaterService;
        this.auditoriumService = auditoriumService;
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
        TheaterDto theaterDto = id == 0 ? new TheaterDto() : theaterService.findTheaterById(id);
        model.addAttribute("theater", theaterDto);
        return "admin/_3_1_theater_page";
    }

    @GetMapping(path = { "/show/auditorium/{id}"})
    public String showAuditorium(
            @PathVariable long id,
            Model model
    ) {
        AuditoriumDto auditoriumDto =  id == 0 ? new AuditoriumDto() : auditoriumService.findAuditoriumById(id);
        model.addAttribute("auditorium", auditoriumDto);
        return "admin/_3_2_auditorium_page";
    }
}
