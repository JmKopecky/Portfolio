package dev.jkopecky.portfolio;

import dev.jkopecky.portfolio.data.ProjectRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPanelController {



    ProjectRepository projectRepository;
    public AdminPanelController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }



    @GetMapping("/admin/panel")
    public String adminPanel(Model model) {
        //todo auth check


        model.addAttribute("projects", projectRepository.findAll());
        return "adminpanel";
    }
}
