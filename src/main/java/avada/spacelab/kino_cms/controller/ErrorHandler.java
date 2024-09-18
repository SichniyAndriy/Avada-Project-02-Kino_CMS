package avada.spacelab.kino_cms.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorHandler implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest req) {
        StringBuilder stringBuilder  =  new StringBuilder("error");
        Optional.ofNullable(req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE))
                .ifPresent(stringBuilder::append);
        return stringBuilder.toString();
    }
}
