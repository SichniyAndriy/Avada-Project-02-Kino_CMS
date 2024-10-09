package avada.spacelab.kino_cms.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/edit")
public class EditPagesController {
    @GetMapping("/main")
    public String editMain(Model model) {
        return "admin/_6_1_edit_main";
    }

    @GetMapping("/about")
    public String editAbout(Model model) {
        return "admin/_6_2_edit_about";
    }

    @GetMapping("/cafe-bar")
    public String editCafeBar(Model model) {
        return "admin/_6_3_edit_cafe-bar";
    }

    @GetMapping("/vip")
    public String editVip(Model model) {
        return "admin/_6_4_edit_vip";
    }

    @GetMapping("/advertising")
    public String editAdvertising(Model model) {
        return "admin/_6_5_edit_advertising";
    }

    @GetMapping("/child-room")
    public String editChildroom(Model model) {
        return "admin/_6_6_edit_child-room";
    }

    @GetMapping("/contacts")
    public String editContacts(Model model) {
        return "admin/_6_7_edit_contacts";
    }
}
