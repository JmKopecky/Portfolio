package dev.jkopecky.portfolio;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProjectController {

    @GetMapping("/projects/condelu")
    public String condelu() {
        return "projects/condelu";
    }
}
