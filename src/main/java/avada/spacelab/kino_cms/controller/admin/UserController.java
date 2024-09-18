package avada.spacelab.kino_cms.controller.admin;

import avada.spacelab.kino_cms.model.dto.UserDto;
import avada.spacelab.kino_cms.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/users")
public class UserController {
    private final UserService userService;

    public UserController(
            @Autowired UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = {"", "/"})
    public String getUsers(Model model) {
        List<UserDto> users = userService.getAllUsers();
        model.addAttribute("users", users);
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

    @GetMapping("/delete/{id}")
    public String deleteUser(
            @PathVariable long id
    ) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

    @PostMapping(path ={"/save", "/save/"})
    public ResponseEntity<String> saveUser(
            @RequestBody UserDto userDto
    ) {
        userService.save(userDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
