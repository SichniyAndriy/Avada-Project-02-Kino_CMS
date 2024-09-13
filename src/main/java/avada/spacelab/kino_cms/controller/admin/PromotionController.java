package avada.spacelab.kino_cms.controller.admin;

import avada.spacelab.kino_cms.model.dto.PromotionsDto;
import avada.spacelab.kino_cms.service.PromotionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
        List<PromotionsDto> promotions = promotionService.getAllPromotions();
        model.addAttribute("promotions", promotions);
        return "admin/_5_promotions";
    }
}
