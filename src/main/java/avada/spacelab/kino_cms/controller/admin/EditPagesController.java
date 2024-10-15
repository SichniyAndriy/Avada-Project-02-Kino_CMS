package avada.spacelab.kino_cms.controller.admin;

import avada.spacelab.kino_cms.model.dto.MainPageInfoDto;
import avada.spacelab.kino_cms.model.entity.MainPageInfo;
import avada.spacelab.kino_cms.model.mapper.MainPageInfoMapper;
import avada.spacelab.kino_cms.service.MainPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/edit")
public class EditPagesController {
    private final MainPageService mainPageService;

    public EditPagesController(
            @Autowired MainPageService mainPageService
    ) {
        this.mainPageService = mainPageService;
    }

    @GetMapping("/main")
    public String editMain(Model model) {
        MainPageInfoDto info = mainPageService.getInfo();
        model.addAttribute("info", info);
        return "admin/_6_1_edit_main";
    }

    @PostMapping("/main")
    public ResponseEntity<HttpStatus> updateMain(
            @ModelAttribute MainPageInfoDto mainPageInfoDto
    ) {
        MainPageInfo mainPageInfo = MainPageInfoMapper.INSTANCE.fromDtoToEntity(mainPageInfoDto);
        mainPageService.saveInfo(mainPageInfo);
        return new ResponseEntity<>(HttpStatus.OK);
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
