package avada.spacelab.kino_cms.controller.user;

import avada.spacelab.kino_cms.model.dto.admin.NewsDto;
import avada.spacelab.kino_cms.model.dto.admin.TheaterDto;
import avada.spacelab.kino_cms.model.dto.user.ContactResponceDto;
import avada.spacelab.kino_cms.service.admin.ContactService;
import avada.spacelab.kino_cms.service.admin.NewsService;
import avada.spacelab.kino_cms.service.admin.TheaterService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class AboutController {

    private final TheaterService theaterService;
    private final NewsService newsService;
    private final ContactService contactService;


    public AboutController(
            @Autowired TheaterService theaterService,
            @Autowired NewsService newsService,
            @Autowired ContactService contactService
    ) {
        this.theaterService = theaterService;
        this.newsService = newsService;
        this.contactService = contactService;
    }

    @GetMapping("/about")
    public String about(Model model) {
        TheaterDto first = theaterService.getAllTheaters().getFirst();
        model.addAttribute("theater", first);
        return "/public/_6_0_about";
    }

    @GetMapping("/news")
    public String news(Model model) {
        List<NewsDto> news = newsService.getActiveNews();
        model.addAttribute("news", news);
        return "/public/_6_1_news";
    }

    @GetMapping("/advert")
    public String advert() {
        return "/public/_6_2_advert";
    }

    @GetMapping("/cafe-bar")
    public String cafeBar() {
        return "/public/_6_3_cafe_bar";
    }

    @GetMapping("/vip-room")
    public String vip() {
        return "/public/_6_4_vip_room";
    }

    @GetMapping("/child-room")
    public String childRoom() {
        return "/public/_6_5_child_room";
    }

    @GetMapping("/mob-apps")
    public String mobApps() {
        return "/public/_6_6_mob_apps";
    }

    @GetMapping("/contacts")
    public String contacts(Model model) {
        List<ContactResponceDto> contactDtos = contactService.getAllWithTheater();
        model.addAttribute("contacts", contactDtos);
        return "/public/_6_7_contacts";
    }

}
