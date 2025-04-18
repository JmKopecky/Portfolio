package dev.jkopecky.portfolio.controllers;

import dev.jkopecky.portfolio.data.Project;
import dev.jkopecky.portfolio.data.ProjectRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewProjectController {


    ProjectRepository projectRepository;
    public ViewProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }


    @GetMapping("/viewproject")
    public String view(Model model, @RequestParam(name = "project") String project) {

        if (project != null && Project.exists(project, projectRepository)) {
            Project p = Project.findProject(project, projectRepository);
            model.addAttribute("project", p);
        } else {
            return "redirect:/error";
        }

        return "projects/viewproject";
    }
}
