package avada.spacelab.kino_cms.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/billboard")
public class BillboardController {

    @GetMapping
    public String getBillboard() {
        return "public/_1_0_billboard";
    }

}
