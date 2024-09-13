package avada.spacelab.kino_cms.controller.admin;

import avada.spacelab.kino_cms.model.dto.NewsDto;
import avada.spacelab.kino_cms.service.NewsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/news")
public class NewsController {
    private final NewsService newsService;

    public NewsController(
            @Autowired NewsService newsService
    ) {
        this.newsService = newsService;
    }

    @GetMapping(path = {"", "/"})
    public String getNews(Model model) {
        List<NewsDto> newsDtoList = newsService.getAllNews();
        model.addAttribute("news", newsDtoList);
        return "admin/_4_news";
    }
}
