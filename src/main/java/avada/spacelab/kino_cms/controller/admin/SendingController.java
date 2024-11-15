package avada.spacelab.kino_cms.controller.admin;

import avada.spacelab.kino_cms.controller.paged.PagedResponse;
import avada.spacelab.kino_cms.controller.util.ControllerUtil;
import avada.spacelab.kino_cms.model.dto.admin.UserDto;
import avada.spacelab.kino_cms.service.admin.UserService;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("admin/sending")
public class SendingController {
    private final UserService userService;
    private final Logger logger = LogManager.getLogger(SendingController.class);

    public SendingController(
            @Autowired UserService userService
    ) {
        this.userService = userService;
    }

    @GetMapping(path = {"", "/"})
    public String index(Model model) {
        long usersAmount = userService.getAllUsersAmount();
        model.addAttribute("usersAmount", usersAmount);

        ArrayList<String> fileNames =
                Arrays.stream(Objects.requireNonNull(new File(ControllerUtil.PATH_TO_SENT_EMAIL).listFiles()))
                .map(File::getName)
                .collect(Collectors.toCollection(ArrayList::new));
        model.addAttribute("fileNames", fileNames);
        logger.info("go to sending page");
        return "admin/_8_0_sending";
    }

    @GetMapping("/pick/{num}")
    public String pick(
            @PathVariable int num,
            Model model
    ) {
        PagedResponse<UserDto> userDtoPage = userService.getUserDtoPage(num, 25);
        model.addAttribute("page", userDtoPage);
        logger.info("go to pick users page");
        return "admin/_8_1_pick_users_page";
    }

    @PostMapping("/save/email")
    public ResponseEntity<String> save(
            @RequestParam String fileName,
            @RequestParam String fileType,
            @RequestParam byte[] file
    ) throws IOException {
        String path = ControllerUtil.saveEmailOnServer(
                fileName,
                fileType,
                file
        );
        logger.info("save email on server");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(path);
    }
}
