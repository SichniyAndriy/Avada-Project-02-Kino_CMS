package avada.spacelab.kino_cms.controller.admin;

import avada.spacelab.kino_cms.controller.paged.PagedResponse;
import avada.spacelab.kino_cms.model.dto.admin.UserDto;
import avada.spacelab.kino_cms.service.admin.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("admin/users")
public class UserController {
    private final UserService userService;

    public UserController(
            @Autowired UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = {"", "/"})
    public String getUserPage(
            @RequestParam(required = false) Integer number,
            @RequestParam(required = false) Integer size,
            Model model
    ) {
        if (number == null) {
            number = 0;
        }
        if (size == null) {
            size = 10;
        }
        PagedResponse<UserDto> page = userService.getUserDtoPage(number, size);
        model.addAttribute("page", page);
        return "admin/_7_0_users";
    }

    @GetMapping("/show/{id}")
    public String showUser(
            @PathVariable long id, Model model
    ) {
        UserDto user = id == 0 ? UserDto.EMPTY() : userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin/_7_1_user_page";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteUser(
            @PathVariable long id
    ) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/save")
    public ResponseEntity<HttpStatus> saveUser(
            @ModelAttribute UserDto user
    ) {
        userService.save(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
