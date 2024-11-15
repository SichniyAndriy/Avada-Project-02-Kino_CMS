package avada.spacelab.kino_cms.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/soon")
public class SoonController {

    @GetMapping
    public String soon() {
        return "public/_2_0_soon";
    }
}
