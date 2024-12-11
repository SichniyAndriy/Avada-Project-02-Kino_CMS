package avada.spacelab.kino_cms.controller.admin;

import avada.spacelab.kino_cms.controller.util.ControllerUtil;
import avada.spacelab.kino_cms.model.entity.MainPageBanner;
import avada.spacelab.kino_cms.model.entity.MainPageBanner.Replacement;
import avada.spacelab.kino_cms.service.admin.MainPageService;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("admin/banners")
public class MainPageController {

    private final MainPageService mainPageService;
    private final String MAIN_PAGE_BANNERS = "kino-cms/pictures/main-page";


    public MainPageController(
            @Autowired MainPageService mainPageService
    ) {
        this.mainPageService = mainPageService;
    }

    @GetMapping(path = { "/", "" })
    public String banners(Model model) {
        List<MainPageBanner> middle =
                mainPageService.getAllByReplacement(MainPageBanner.Replacement.SLASH_BANNER);
        String background = !middle.isEmpty() ? middle.get(0).getPath() : null;
        List<MainPageBanner> banners =
                mainPageService.getAllByReplacement(Replacement.UP_BANNER);
        List<MainPageBanner> promotions =
                mainPageService.getAllByReplacement(Replacement.BOTTOM_PROMOTION);

        model.addAttribute("banners", banners);
        model.addAttribute("background", background);
        model.addAttribute("promotions", promotions);
        return "admin/_1_banners";
    }

    @PostMapping("/add")
    public ResponseEntity<String> saveNewFile(
            @RequestParam MultipartFile file,
            @RequestParam String timestamp,
            @RequestParam String ext
    ) throws IOException {

        String resName = ControllerUtil.savePictureOnServer(
                MAIN_PAGE_BANNERS, file.getOriginalFilename(), timestamp, ext, file
        );
        return ResponseEntity.ok(resName);
    }

    @PutMapping("/update/up-banners")
    public ResponseEntity<HttpStatus> updateBanners(
            @RequestBody List<MainPageBanner> banners
    ) {
        mainPageService.deleteAllByReplacement(Replacement.UP_BANNER);
        banners.forEach(item -> item.setPlace(Replacement.UP_BANNER));
        mainPageService.saveAll(banners);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("update/down-promotions")
    public ResponseEntity<HttpStatus> updatePromotions(
            @RequestBody List<MainPageBanner> promotions
    ) {
        mainPageService.deleteAllByReplacement(Replacement.BOTTOM_PROMOTION);
        promotions.forEach(item -> item.setPlace(Replacement.BOTTOM_PROMOTION));
        mainPageService.saveAll(promotions);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("update/background")
    public ResponseEntity<HttpStatus> updatePromotions(
            @RequestBody MainPageBanner background
    ) {
        mainPageService.deleteAllByReplacement(Replacement.SLASH_BANNER);
        background.setPlace(Replacement.SLASH_BANNER);
        mainPageService.save(background);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}


