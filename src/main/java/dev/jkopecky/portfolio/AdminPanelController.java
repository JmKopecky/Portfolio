package dev.jkopecky.portfolio;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPanelController {

    @GetMapping("/admin/panel")
    public static String adminPanel(Model model) {
        //todo auth check
        return "adminpanel";
    }
}
