package dev.jkopecky.portfolio;

import dev.jkopecky.portfolio.data.ProjectRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    ProjectRepository projectRepository;
    public HomeController(ProjectRepository projectRepository) {
        PortfolioApplication.createProjects(projectRepository);
        this.projectRepository = projectRepository;
    }

    @GetMapping("/")
    public String home(Model model) {



        model.addAttribute("projects", projectRepository.findAll());
        return "home";
    }
}
