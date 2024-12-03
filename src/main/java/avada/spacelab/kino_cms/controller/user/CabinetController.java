package avada.spacelab.kino_cms.controller.user;

import avada.spacelab.kino_cms.model.dto.admin.UserDto;
import avada.spacelab.kino_cms.service.admin.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cabinet")
public class CabinetController {

    private final UserService userService;

    public CabinetController(
            @Autowired UserService userService
    ) {
        this.userService = userService;
    }


    @GetMapping
    public String getCabinet(
            Model model
    ) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        UserDetails principal = (UserDetails) auth.getPrincipal();
        String email = principal.getUsername();
        UserDto userByEmail = userService.getUserByEmail(email);
        model.addAttribute("user", userByEmail);
        return "public/_8_0_cabinet";
    }

    @PostMapping
    public ResponseEntity<HttpStatus> postCabinet(
            @ModelAttribute UserDto userDto,
            @RequestParam String confirmPassword
    ) {
        if(!userDto.passHash().equals(confirmPassword)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        userService.save(userDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
