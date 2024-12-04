package avada.spacelab.kino_cms.controller.admin;

import avada.spacelab.kino_cms.controller.util.ControllerUtil;
import avada.spacelab.kino_cms.model.dto.admin.AuditoriumDto;
import avada.spacelab.kino_cms.model.dto.admin.TheaterDto;
import avada.spacelab.kino_cms.service.admin.AuditoriumService;
import avada.spacelab.kino_cms.service.admin.TheaterService;
import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("admin/theaters")
public class TheaterController {

    private final TheaterService theaterService;
    private final AuditoriumService auditoriumService;
    private final String PATH_TO_THEATERS = "pictures/theaters";


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
                TheaterDto.EMPTY() : theaterService.getById(id);
        model.addAttribute("theater", theater);
        return "admin/_3_1_theater_page";
    }

    @PostMapping("/save")
    public String saveTheater(
            @ModelAttribute TheaterDto theater,
            @RequestParam String picturesJson
    ) {
        theaterService.save(theater, picturesJson);
        return "redirect:/admin/theaters";
    }

    @GetMapping("/delete/{id}")
    public String deleteTheaterById(
            @PathVariable long id
    ) {
        theaterService.deleteById(id);
        return "redirect:/admin/theaters";
    }

    @GetMapping(path = { "/show/auditorium/{id}/{theaterId}"})
    public String showAuditorium(
            @PathVariable long id,
            @PathVariable long theaterId,
            Model model
    ) {
        AuditoriumDto auditoriumDto = (id == 0) ?
                AuditoriumDto.EMPTY() : auditoriumService.getById(id);
        model.addAttribute("auditorium", auditoriumDto);
        model.addAttribute("theaterId", theaterId);
        return "admin/_3_2_auditorium_page";
    }

    @DeleteMapping("/delete/auditorium/{audId}")
    public ResponseEntity<HttpStatus> deleteAuditorium(
            @PathVariable long audId
    ) {
        auditoriumService.deleteAuditoriumById(audId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/save/auditorium")
    public ResponseEntity<HttpStatus> saveAuditorium(
            @ModelAttribute AuditoriumDto auditoriumDto,
            @RequestParam String picturesJson
    ) {
        auditoriumService.save(auditoriumDto, picturesJson);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping(path ={"/save/file", "/save/auditorium/file"})
    public ResponseEntity<String> saveAuditorium(
            @RequestParam MultipartFile file,
            @RequestParam String timestamp,
            @RequestParam String ext
    ) throws IOException {
        String res = ControllerUtil.savePictureOnServer(
                PATH_TO_THEATERS, file.getOriginalFilename(), timestamp, ext, file
        );
        return ResponseEntity.ok(res);
    }

}
