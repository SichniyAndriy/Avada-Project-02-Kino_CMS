package avada.spacelab.kino_cms.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/banners")
public class BannerController {
    @GetMapping(path = {"/", ""})

    public String banners() {
        return "admin/_1_banners";
    }
}
