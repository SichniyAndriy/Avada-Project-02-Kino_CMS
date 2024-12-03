package avada.spacelab.kino_cms.controller.user;

import avada.spacelab.kino_cms.model.dto.admin.AuditoriumDto;
import avada.spacelab.kino_cms.model.dto.admin.TheaterDto;
import avada.spacelab.kino_cms.model.dto.user.TheaterResponceDto;
import avada.spacelab.kino_cms.service.admin.AuditoriumService;
import avada.spacelab.kino_cms.service.admin.TheaterService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/theaters")
public class UserTheaterController {

    private final TheaterService theaterService;
    private final AuditoriumService auditoriumService;


    public UserTheaterController(
            @Autowired TheaterService theaterService,
            @Autowired AuditoriumService auditoriumService
    ) {
        this.theaterService = theaterService;
        this.auditoriumService = auditoriumService;
    }

    @GetMapping
    public String theaters(Model model) {
        List<TheaterResponceDto> responceDtos = theaterService.getAllTheaterResponceDtos();
        model.addAttribute("theaters", responceDtos);
        return "public/_4_0_theaters";
    }

    @GetMapping("/show/{id}")
    public String showTheater(
            Model model,
            @PathVariable long id
            ) {
        TheaterDto theaterDto = theaterService.getById(id);
        List<AuditoriumDto> auditoriumDtos = auditoriumService.getAuditoriumsByTheaterId(id);
        model.addAttribute("theater", theaterDto);
        model.addAttribute("auditoriums", auditoriumDtos);
        return "public/_4_1_theater";
    }

    @GetMapping("/show/aud/{id}")
    public String showAuditorium(
            Model model,
            @PathVariable long id
    ) {
        AuditoriumDto auditorium = auditoriumService.getById(id);
        model.addAttribute("auditorium", auditorium);
        return "public/_4_2_auditorium";
    }

}
