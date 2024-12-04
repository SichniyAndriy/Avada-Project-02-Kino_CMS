package avada.spacelab.kino_cms.controller.user;

import avada.spacelab.kino_cms.service.admin.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;


    public RegistrationController(
            @Autowired UserService userService
    ) {
        this.userService = userService;
    }

    @GetMapping
    public String register() {
        return "public/_7_0_registration";
    }

    @PostMapping
    public ResponseEntity<HttpStatus> register(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String confirmedPassword
    ) {
        if (!password.equals(confirmedPassword)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        userService.saveNewUser(email, password);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

}
