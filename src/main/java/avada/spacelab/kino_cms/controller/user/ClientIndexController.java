package avada.spacelab.kino_cms.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = {"", "/", "/index"})
public class ClientIndexController {

    @GetMapping
    public String index() {
        return "public/index";
    }

}
