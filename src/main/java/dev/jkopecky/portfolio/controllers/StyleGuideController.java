package dev.jkopecky.portfolio.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StyleGuideController {

    @GetMapping("/styleguide")
    public String styleguide(Model model) {
        return "styleguide";
    }
}
