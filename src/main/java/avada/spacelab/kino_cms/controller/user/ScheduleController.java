package avada.spacelab.kino_cms.controller.user;

import avada.spacelab.kino_cms.model.dto.user.SchedulePageResponceDto;
import avada.spacelab.kino_cms.service.user.SchedulePageService;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {
    private final SchedulePageService schedulePageService;

    public ScheduleController(
            @Autowired SchedulePageService schedulePageService
    ) {
        this.schedulePageService = schedulePageService;
    }

    @GetMapping
    public String showSchedule(Model model) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();

        Callable<ConcurrentMap<LocalDate, List<SchedulePageResponceDto>>> entriesCallable =
                schedulePageService::getTableEntries;
        Future<ConcurrentMap<LocalDate, List<SchedulePageResponceDto>>> entriesFuture =
                executor.submit(entriesCallable);

        Callable<List<String>> moviesCallable = schedulePageService::movies;
        Future<List<String>> moviesfuture = executor.submit(moviesCallable);

        Callable<List<String>> theatersCallable = schedulePageService::theaters;
        Future<List<String>> theatersfuture = executor.submit(theatersCallable);

        Callable<List<String>> auditoriumsCallable = schedulePageService::auditoriums;
        Future<List<String>> auditoriumsfuture = executor.submit(auditoriumsCallable);

        List<String> movies = moviesfuture.get();
        List<String> theaters = theatersfuture.get();
        List<String> auditoriums = auditoriumsfuture.get();
        ConcurrentMap<LocalDate, List<SchedulePageResponceDto>> tables = entriesFuture.get();

        executor.shutdown();

        model.addAttribute("movies", movies);
        model.addAttribute("theaters", theaters);
        model.addAttribute("auditoriums", auditoriums);
        model.addAttribute("tables", tables);

        return "public/_3_0_schedule";
    }

}
