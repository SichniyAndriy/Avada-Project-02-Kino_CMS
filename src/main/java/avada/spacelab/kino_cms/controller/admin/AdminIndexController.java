package avada.spacelab.kino_cms.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminIndexController {

    @GetMapping(path = {"/index", "/", ""})
    public String index() {
        return "admin/index";
    }

}
