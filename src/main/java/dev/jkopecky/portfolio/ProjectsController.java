package dev.jkopecky.portfolio;

import dev.jkopecky.portfolio.data.ProjectRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProjectsController {

    ProjectRepository projectRepository;
    public ProjectsController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }


    @GetMapping("/projects")
    public String projects(Model model) {
        model.addAttribute("projects", projectRepository.findAll());
        return "projects/projects";
    }
}
