package avada.spacelab.kino_cms.controller.admin;

import avada.spacelab.kino_cms.model.dto.NewsDto;
import avada.spacelab.kino_cms.service.NewsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        List<NewsDto> newsList = newsService.getAllNews();
        model.addAttribute("newsList", newsList);
        return "admin/_4_0_news";
    }

    @GetMapping("/show/{id}")
    public String showNews(
            @PathVariable int id,
            Model model
    ) {
        NewsDto newsById = id == 0 ? NewsDto.EMPTY() : newsService.getNewsById(id);
        model.addAttribute("news", newsById);
        return "admin/_4_1_news_page";
    }
}
