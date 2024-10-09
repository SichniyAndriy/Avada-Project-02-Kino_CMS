package avada.spacelab.kino_cms.controller.admin;

import avada.spacelab.kino_cms.controller.util.ControllerUtil;
import avada.spacelab.kino_cms.model.entity.MainPageBanners;
import avada.spacelab.kino_cms.model.entity.MainPageBanners.Replacement;
import avada.spacelab.kino_cms.service.MainPageBannersService;
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
public class MainPageBannersController {
    private final MainPageBannersService mainPageBannersService;

    private final String MAIN_PAGE_BANNERS = "pictures/main-page/";

    public MainPageBannersController(
            @Autowired MainPageBannersService mainPageBannersService
    ) {
        this.mainPageBannersService = mainPageBannersService;
    }

    @GetMapping(path = {"/", ""})
    public String banners(Model model) {
        List<MainPageBanners> middle =
                mainPageBannersService.getAllByReplacement(MainPageBanners.Replacement.SLASH_BANNER);
        String background = !middle.isEmpty() ? middle.getFirst().getPath() : null;
        List<MainPageBanners> banners =
                mainPageBannersService.getAllByReplacement(Replacement.UP_BANNER);
        List<MainPageBanners> promotions =
                mainPageBannersService.getAllByReplacement(Replacement.BOTTOM_PROMOTION);

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
            @RequestBody List<MainPageBanners> banners
    ) {
        mainPageBannersService.deleteAllByReplacement(Replacement.UP_BANNER);
        banners.forEach(item -> item.setPlace(Replacement.UP_BANNER));
        mainPageBannersService.saveAll(banners);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("update/down-promotions")
    public ResponseEntity<HttpStatus> updatePromotions(
            @RequestBody List<MainPageBanners> promotions
    ) {
        mainPageBannersService.deleteAllByReplacement(Replacement.BOTTOM_PROMOTION);
        promotions.forEach(item -> item.setPlace(Replacement.BOTTOM_PROMOTION));
        mainPageBannersService.saveAll(promotions);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("update/background")
    public ResponseEntity<HttpStatus> updatePromotions(
            @RequestBody MainPageBanners background
    ) {
        mainPageBannersService.deleteAllByReplacement(Replacement.SLASH_BANNER);
        background.setPlace(Replacement.SLASH_BANNER);
        mainPageBannersService.save(background);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}


