package avada.spacelab.kino_cms.controller.admin;

import avada.spacelab.kino_cms.service.admin.impl.AdminIndexServiceImpl;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminIndexController {

    private final AdminIndexServiceImpl adminIndexService;


    public AdminIndexController(
            @Autowired AdminIndexServiceImpl adminIndexService
    ) {
        this.adminIndexService = adminIndexService;
    }

    @GetMapping(path = {"/index", "/", ""})
    public String index(Model model) {
        final Faker faker = new Faker();
        long amountMovies = adminIndexService.countMovies();
        long amountTheaters = adminIndexService.countTheaters();
        long amountAuditoriums = adminIndexService.countAuditoriums();
        long amountUsers = adminIndexService.countUsers();
        long amountWomen = adminIndexService.countWomen();
        long amountMen = adminIndexService.countMen();
        long visitors = faker.number().numberBetween(5_000, 25_000);
        long registeredVisitors = faker.number().numberBetween(1_000, 5_000);


        model.addAttribute("amountMovies", amountMovies);
        model.addAttribute("amountTheaters", amountTheaters);
        model.addAttribute("amountAuditoriums", amountAuditoriums);
        model.addAttribute("amountUsers", amountUsers);
        model.addAttribute("amountWomen", amountWomen);
        model.addAttribute("amountMen", amountMen);
        model.addAttribute("visitors", visitors);
        model.addAttribute("registeredVisitors", registeredVisitors);

        return "admin/index";
    }

}
