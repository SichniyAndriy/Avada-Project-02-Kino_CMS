package avada.spacelab.kino_cms.controller.admin;

import avada.spacelab.kino_cms.model.dto.PromotionDto;
import avada.spacelab.kino_cms.service.PromotionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/promotions")
public class PromotionController {
    private final PromotionService promotionService;

    public PromotionController(
            @Autowired PromotionService promotionService
    ) {
        this.promotionService = promotionService;
    }

    @GetMapping(path = {"", "/"})
    public String promotions(Model model) {
        List<PromotionDto> promotions = promotionService.getAllPromotions();
        model.addAttribute("promotions", promotions);
        return "admin/_5_0_promotions";
    }

    @GetMapping("/show/{id}")
    public String showPromotion(
            @PathVariable int id, Model model
    ) {
        PromotionDto promotion = id == 0 ?
                PromotionDto.EMPTY() : promotionService.getPromotionById(id);
        model.addAttribute("promotion", promotion);
        return "admin/_5_1_promotion_page";
    }
}
