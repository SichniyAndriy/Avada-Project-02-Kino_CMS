package avada.spacelab.kino_cms.controller.admin;

import avada.spacelab.kino_cms.model.dto.AuditoriumDto;
import avada.spacelab.kino_cms.model.dto.TheaterDto;
import avada.spacelab.kino_cms.service.AuditoriumService;
import avada.spacelab.kino_cms.service.TheaterService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/theaters")
public class TheaterController {
    private final TheaterService theaterService;
    private final AuditoriumService auditoriumService;

    public TheaterController(
            @Autowired TheaterService theaterService,
            @Autowired AuditoriumService auditoriumService
    ) {
        this.theaterService = theaterService;
        this.auditoriumService = auditoriumService;
    }

    @GetMapping(path = {"", "/"})
    public String theaters(Model model) {
        List<TheaterDto> theaters = theaterService.getAllTheaters();
        model.addAttribute("theaters", theaters);
        return "admin/_3_0_theaters";
    }

    @GetMapping("/show/{id}")
    public String showTheater(
            @PathVariable long id,
            Model model
    ) {
        TheaterDto theater = (id == 0) ?
                TheaterDto.EMPTY() : theaterService.findTheaterById(id);
        model.addAttribute("theater", theater);
        return "admin/_3_1_theater_page";
    }

    @GetMapping(path = { "/show/auditorium/{id}"})
    public String showAuditorium(
            @PathVariable long id,
            Model model
    ) {
        AuditoriumDto auditoriumDto = (id == 0) ?
                AuditoriumDto.EMPTY() : auditoriumService.findAuditoriumById(id);
        model.addAttribute("auditorium", auditoriumDto);
        return "admin/_3_2_auditorium_page";
    }

    @DeleteMapping("/delete/auditorium/{audId}")
    public ResponseEntity<HttpStatus> deleteAuditorium(
            @PathVariable long audId
    ) {
        auditoriumService.deleteAuditoriumById(audId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/save")
    public String saveTheater(
            @ModelAttribute TheaterDto theater
    ) {
        theaterService.save(theater);
        return "redirect:/admin/theaters";
    }
}
