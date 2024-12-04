package avada.spacelab.kino_cms.controller.admin;

import avada.spacelab.kino_cms.controller.util.ControllerUtil;
import avada.spacelab.kino_cms.model.dto.admin.NewsDto;
import avada.spacelab.kino_cms.service.admin.NewsService;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("admin/news")
public class NewsController {

    private final NewsService newsService;
    private final String NEWS_PATH = "pictures/news";


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
            @PathVariable long id,
            Model model
    ) {
        NewsDto newsById = id == 0 ? NewsDto.EMPTY() : newsService.getById(id);
        model.addAttribute("news", newsById);
        return "admin/_4_1_news_page";
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteNews(
            @PathVariable long id
    ) {
        newsService.deleteById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/save/file")
    public ResponseEntity<String> saveFile(
            @RequestBody MultipartFile picture,
            @RequestParam String timestamp,
            @RequestParam String ext
    ) throws IOException {
        String res = ControllerUtil.savePictureOnServer(
                NEWS_PATH, picture.getOriginalFilename(), timestamp, ext, picture
        );
        return ResponseEntity.ok(res);
    }

    @PostMapping("/save")
    public ResponseEntity<HttpStatus> saveNews(
            @ModelAttribute NewsDto news
    ) {
        newsService.save(news);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
