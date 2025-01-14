package dev.jkopecky.portfolio;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.jkopecky.portfolio.data.Project;
import dev.jkopecky.portfolio.data.ProjectRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.naming.AuthenticationException;
import java.util.HashMap;


@Controller
public class EditProjectController {


    ProjectRepository projectRepository;
    public EditProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }


    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("projects", projectRepository.findAll());
        return "projects/editproject";
    }

    @PostMapping("/create")
    public ResponseEntity<HashMap<String, Object>> post(@RequestBody String requestBody) {
        String title = "";
        String mode = null;
        HashMap<String, String> data = new HashMap<>();
        HashMap<String, Object> response = new HashMap<>();
        response.put("error", "none");
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(requestBody);
            mode = node.get("mode").asText();
            if (mode.equals("retrieve")) {
                title = node.get("search").asText();
            }
            if (mode.equals("update")) {
                title = node.get("resource").asText();
                data.put("resource", title);
                data.put("displayTitle", node.get("newDisplayTitle").asText());
                data.put("desc", node.get("newDesc").asText());
                data.put("image", node.get("newImage").asText());
                data.put("content", node.get("newContent").asText());
                data.put("techs", node.get("newTech").asText());
            }
        } catch (Exception e) {
            response.put("error", "invalid title");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        if (mode != null && mode.equals("retrieve")) {
            return retrieveProject(title, response);
        }

        if (mode != null && mode.equals("update")) {
            return updateProject(data, response);
        }

        response.put("error", "invalid operation");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    private ResponseEntity<HashMap<String, Object>> retrieveProject(String target, HashMap<String, Object> response) {
        Project project = null;
        for (Project p : projectRepository.findAll()) {
            if (p.getTitle().equals(target)) {
                project = p;
                break;
            }
        }

        if (project == null) {
            response.put("error", "project not found");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        response.put("title", project.getDisplayName());
        response.put("resource", project.getTitle());
        response.put("shortdesc", project.getShortDesc());
        response.put("imageurl", project.getImageUrl());
        response.put("techlist", project.getTechList());
        response.put("content", project.retrieveHTML());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    private ResponseEntity<HashMap<String, Object>> updateProject(HashMap<String, String> data, HashMap<String, Object> response) {
        Project project = null;
        for (Project p : projectRepository.findAll()) {
            if (p.getTitle().equals(data.get("resource"))) {
                project = p;
            }
        }
        if (project == null) {
            //creating new project
            try {
                project = Project.createProject(
                        data.get("displayTitle"), projectRepository);
                project.initDefaults(
                        data.get("desc"),
                        data.get("image"),
                        "/",
                        "/viewproject?project=" + project.getTitle());
                project.parseTech(data.get("techs"));
            } catch (Exception e) {
                e.printStackTrace();
                response.put("error", "failed to create new project");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

        } else {
            project.setDisplayName(data.get("displayTitle"));
            project.setShortDesc(data.get("desc"));
            project.setImageUrl(data.get("image"));
            project.writeHTML(data.get("content"));
            project.parseTech(data.get("techs"));
        }

        project.saveProject(projectRepository);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
