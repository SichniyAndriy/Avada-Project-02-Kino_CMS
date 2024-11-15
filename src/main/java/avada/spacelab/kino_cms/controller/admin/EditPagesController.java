package avada.spacelab.kino_cms.controller.admin;

import avada.spacelab.kino_cms.controller.util.ControllerUtil;
import avada.spacelab.kino_cms.model.dto.admin.ContactDto;
import avada.spacelab.kino_cms.model.dto.admin.EditPageDto;
import avada.spacelab.kino_cms.model.dto.admin.MainPageInfoDto;
import avada.spacelab.kino_cms.service.admin.ContactService;
import avada.spacelab.kino_cms.service.admin.EditPageService;
import avada.spacelab.kino_cms.service.admin.MainPageService;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("admin/edit")
public class EditPagesController {
    private final String PATH_TO_EDIT = "pictures/edit-page";

    private final MainPageService mainPageService;
    private final ContactService contactService;
    private final EditPageService editPageService;

    public EditPagesController(
            @Autowired MainPageService mainPageService,
            @Autowired ContactService contactService,
            @Autowired EditPageService editPageService
    ) {
        this.mainPageService = mainPageService;
        this.contactService = contactService;
        this.editPageService = editPageService;
    }

    //========================\ Main /========================\\

    @GetMapping("/main")
    public String editMain(Model model) {
        MainPageInfoDto info = mainPageService.getInfo();
        model.addAttribute("info", info);
        return "admin/_6_1_edit_main";
    }

    @PostMapping("/main")
    public ResponseEntity<HttpStatus> updateMain(
            @ModelAttribute MainPageInfoDto info
    ) {
        mainPageService.saveInfoDto(info);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //========================\ About /========================\\

    @GetMapping("/about")
    public String editAbout(Model model) {
        EditPageDto aboutPage = editPageService.getAbout();
        model.addAttribute("page", aboutPage);
        model.addAttribute("page_name", "about");
        return "admin/_6_2_edit_about";
    }

    @PostMapping("/about")
    public ResponseEntity<HttpStatus> updateAbout(
            @ModelAttribute EditPageDto about,
            @RequestParam String picturesJson
    ) {
        editPageService.saveAbout(about, picturesJson);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    //========================\ Cafe-Bar /========================\\

    @GetMapping("/cafe-bar")
    public String editCafeBar(Model model) {
        EditPageDto page = editPageService.getCafeBar();
        model.addAttribute("page", page);
        model.addAttribute("page_name", "cafe-bar");
        return "admin/_6_3_edit_cafe-bar";
    }

    @PostMapping("/cafe-bar")
    public ResponseEntity<HttpStatus> updateCafeBar(
            @ModelAttribute EditPageDto cafe,
            @RequestParam String picturesJson
    ) {
        editPageService.saveCafeBar(cafe, picturesJson);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    //========================\ VIP-Room /========================\\

    @GetMapping("/vip-room")
    public String editVip(Model model) {
        EditPageDto page = editPageService.getVipRoom();
        model.addAttribute("page", page);
        model.addAttribute("page_name", "vip-room");
        return "admin/_6_4_edit_vip";
    }

    @PostMapping("/vip-room")
    public ResponseEntity<HttpStatus> updateVipRoom(
            @ModelAttribute EditPageDto vipRoom,
            @RequestParam String picturesJson
    ) {
        editPageService.saveVipRoom(vipRoom, picturesJson);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    //========================\ Advertising /========================\\

    @GetMapping("/advertising")
    public String editAdvertising(Model model) {
        model.addAttribute("page", editPageService.getAdvertising());
        model.addAttribute("page_name", "advertising");
        return "admin/_6_5_edit_advertising";
    }

    @PostMapping("/advertising")
    public ResponseEntity<HttpStatus> updateAdvertising(
            @ModelAttribute EditPageDto vipRoom,
            @RequestParam String picturesJson
    ) {
        editPageService.saveAdvertising(vipRoom, picturesJson);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    //========================\ Child-Room /========================\\

    @GetMapping("/child-room")
    public String editChildRoom(Model model) {
        EditPageDto editPageDto = editPageService.getChildRoom();
        model.addAttribute("page", editPageDto);
        model.addAttribute("page_name", "child-room");
        return "admin/_6_6_edit_child-room";
    }

    @PostMapping("/child-room")
    public ResponseEntity<HttpStatus> updateChildRoom(
            @ModelAttribute EditPageDto vipRoom,
            @RequestParam String picturesJson
    ) {
        editPageService.saveChildRoom(vipRoom, picturesJson);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    //========================\ Contacts /========================\\

    @GetMapping("/contacts")
    public String editContacts(Model model) {
        List<ContactDto> contactDtos = contactService.getAll();
        model.addAttribute("contacts", contactDtos);
        return "admin/_6_7_edit_contacts";
    }

    @PostMapping("/contacts")
    public ResponseEntity<HttpStatus> updateContacts(
            @RequestBody List<ContactDto> contacts
    ) {
        contactService.saveList(contacts);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //========================\ Save file /========================\\

    @PostMapping("/save/file")
    public ResponseEntity<String> saveFile(
            @RequestParam MultipartFile file,
            @RequestParam String timestamp,
            @RequestParam String ext
    ) throws IOException {
        String res = ControllerUtil.savePictureOnServer(
                PATH_TO_EDIT, file.getOriginalFilename(), timestamp, ext, file
        );
        return ResponseEntity.ok(res);
    }
}
