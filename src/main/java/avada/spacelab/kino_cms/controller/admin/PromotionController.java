package avada.spacelab.kino_cms.controller.admin;

import avada.spacelab.kino_cms.controller.util.ControllerUtil;
import avada.spacelab.kino_cms.model.dto.admin.PromotionDto;
import avada.spacelab.kino_cms.service.admin.PromotionService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("admin/promotions")
public class PromotionController {

    private final PromotionService promotionService;
    private final String PATH_TO_PROMOTIONS = "pictures/promotions";


    public PromotionController(
            @Autowired PromotionService promotionService
    ) {
        this.promotionService = promotionService;
    }

    @GetMapping(path = { "", "/" })
    public String promotions(Model model) {
        List<PromotionDto> promotions = promotionService.getAllPromotions();
        model.addAttribute("promotions", promotions);
        return "admin/_5_0_promotions";
    }

    @GetMapping("/show/{id}")
    public String showPromotion(
            @PathVariable long id,
            Model model
    ) {
        PromotionDto promotion = id == 0 ?
                PromotionDto.EMPTY() : promotionService.getPromotionById(id);
        model.addAttribute("promotion", promotion);
        return "admin/_5_1_promotion_page";
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deletePromotion(
            @PathVariable long id
    ) {
        promotionService.deleteById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("save/file")
    public ResponseEntity<String> savePicture(
            @RequestParam MultipartFile picture,
            @RequestParam String timestamp,
            @RequestParam String ext
    ) throws IOException {
        String res = ControllerUtil.savePictureOnServer(
                PATH_TO_PROMOTIONS, picture.getOriginalFilename(), timestamp, ext, picture
        );
        return ResponseEntity.ok(res);
    }

    @PostMapping("/save")
    public ResponseEntity<HttpStatus> savePromotion(
            @ModelAttribute PromotionDto promotion
    ) {
        promotionService.save(promotion);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
