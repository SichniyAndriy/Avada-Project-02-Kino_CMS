package avada.spacelab.kino_cms.controller.user;

import avada.spacelab.kino_cms.model.dto.admin.PromotionDto;
import avada.spacelab.kino_cms.service.admin.PromotionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/promotions")
public class PromotionsController {

    private final PromotionService promotionService;


    public PromotionsController(
            @Autowired PromotionService promotionService
    ) {
        this.promotionService = promotionService;
    }

    @GetMapping
    public String promotions(Model model) {
        List<PromotionDto> promotions = promotionService.getActivePromotions();
        model.addAttribute("promotions", promotions);
        return "public/_5_0_promotions";
    }

    @GetMapping("/{id}")
    public String promotion(
            Model model,
            @PathVariable int id) {
        PromotionDto promotion = promotionService.getPromotionById(id);
        model.addAttribute("promotion", promotion);
        return "public/_5_1_promotion";
    }

}
