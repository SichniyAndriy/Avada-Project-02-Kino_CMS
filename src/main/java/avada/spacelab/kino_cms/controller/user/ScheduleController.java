package avada.spacelab.kino_cms.controller.user;

import avada.spacelab.kino_cms.model.dto.admin.AuditoriumDto;
import avada.spacelab.kino_cms.model.dto.admin.MovieDto;
import avada.spacelab.kino_cms.model.dto.user.SchedulePageResponceDto;
import avada.spacelab.kino_cms.service.admin.AuditoriumService;
import avada.spacelab.kino_cms.service.admin.MovieService;
import avada.spacelab.kino_cms.service.user.SchedulePageService;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {

    private final SchedulePageService schedulePageService;
    private final AuditoriumService auditoriumService;
    private final MovieService movieService;

    public ScheduleController(
            @Autowired SchedulePageService schedulePageService,
            @Autowired AuditoriumService auditoriumService,
            @Autowired MovieService movieService
    ) {
        this.schedulePageService = schedulePageService;
        this.auditoriumService = auditoriumService;
        this.movieService = movieService;
    }

    @GetMapping
    public String showSchedule(Model model) {
        ConcurrentMap<LocalDate, List<SchedulePageResponceDto>> tables =
                schedulePageService.getTableEntries();
        formModel(model);
        model.addAttribute("tables", tables);
        return "public/_3_0_schedule";
    }

    @PostMapping("/choice")
    public String showScheduleChoice(
            Model model,
            @RequestParam boolean is2D,
            @RequestParam boolean is3D,
            @RequestParam boolean isImax,
            @RequestParam String theater,
            @RequestParam String date,
            @RequestParam String movie,
            @RequestParam String auditorium
    ) {
        ConcurrentMap<LocalDate, List<SchedulePageResponceDto>> choice =
                schedulePageService.getChoiceResponce(is2D, is3D, isImax, theater, date, movie, auditorium);
        model.addAttribute("tables", choice);
        return "public/_3_0_schedule :: scheduleTables";
    }

    @GetMapping("/booking/{date}/{time}/{movieId}/{theater}/{auditoriumString}/{price}")
    public String bookingPage(
            Model model,
            @PathVariable String date,
            @PathVariable String time,
            @PathVariable long movieId,
            @PathVariable String theater,
            @PathVariable String auditoriumString,
            @PathVariable String price
    ) {
        AuditoriumDto auditoriumDto =
                auditoriumService.getByTheaterAndNumber(theater, Integer.parseInt(auditoriumString));
        MovieDto movieDto =
                movieService.getMovieById(movieId);

        model.addAttribute("date", LocalDate.parse(date));
        model.addAttribute("time", time);
        model.addAttribute("theater", theater);
        model.addAttribute("movie", movieDto);
        model.addAttribute("auditorium", auditoriumDto);
        model.addAttribute("price", price);

        return "public/_3_1_booking";
    }

    private void formModel(Model model) {
        List<String> movies = schedulePageService.movies();
        List<String> theaters = schedulePageService.theaters();
        List<String> auditoriums = schedulePageService.auditoriums();
        List<String> dates = schedulePageService.dates();

        model.addAttribute("movies", movies);
        model.addAttribute("theaters", theaters);
        model.addAttribute("auditoriums", auditoriums);
        model.addAttribute("dates", dates);
    }

}
