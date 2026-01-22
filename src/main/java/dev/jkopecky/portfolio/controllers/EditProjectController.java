package dev.jkopecky.portfolio.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.jkopecky.portfolio.data.Project;
import dev.jkopecky.portfolio.data.ProjectRepository;
import dev.jkopecky.portfolio.data.SessionToken;
import dev.jkopecky.portfolio.data.SessionTokenRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@Controller
public class EditProjectController {


    ProjectRepository projectRepository;
    SessionTokenRepository sessionTokenRepository;
    public EditProjectController(ProjectRepository projectRepository, SessionTokenRepository sessionTokenRepository) {
        this.projectRepository = projectRepository;
        this.sessionTokenRepository = sessionTokenRepository;
    }


    @PostMapping("/admin/retrieveproject")
    public ResponseEntity<HashMap<String, Object>> retreiveProjectData(@RequestParam(name = "target", required = true) String target, @CookieValue(value = "sessiontoken", defaultValue = "null") String sessionToken) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("error", "none");

        //auth check
        boolean authenticated = false;
        for (SessionToken token : sessionTokenRepository.findAll()) {
            if (token.getToken().equals(sessionToken)) {
                authenticated = true;
            }
        }
        if (!authenticated) {
            response.put("error", "unauthenticated");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }


        return retrieveProject(target, response);
    }



    @PostMapping("/admin/updateproject")
    public ResponseEntity<HashMap<String, Object>> updateProject(@RequestBody String requestBody, @CookieValue(value = "sessiontoken", defaultValue = "null") String sessionToken) {
        String title = "";
        HashMap<String, String> data = new HashMap<>();
        HashMap<String, Object> response = new HashMap<>();
        response.put("error", "none");

        //auth check
        boolean authenticated = false;
        for (SessionToken token : sessionTokenRepository.findAll()) {
            if (token.getToken().equals(sessionToken)) {
                authenticated = true;
            }
        }
        if (!authenticated) {
            response.put("error", "unauthenticated");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        //data retrieval
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(requestBody);
            title = node.get("resource").asText();
            data.put("resource", title);
            data.put("displayTitle", node.get("newDisplayTitle").asText());
            data.put("desc", node.get("newDesc").asText());
            data.put("image", node.get("newImage").asText());
            data.put("content", node.get("newContent").asText());
            data.put("techs", node.get("newTech").asText());
            data.put("links", node.get("newLink").asText());
        } catch (Exception e) {
            response.put("error", "invalid title");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        return updateProject(data, response);
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
        response.put("linklist", project.getLinkList());
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
                project.parseLinks(data.get("links"));
            } catch (Exception e) {
                e.printStackTrace();
                response.put("error", "failed to create new project");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

        } else {
            project.setDisplayName(data.get("displayTitle"));
            project.setShortDesc(data.get("desc"));
            project.setImageUrl(data.get("image"));
            project.writeHTML(data.get("content"), projectRepository);
            project.parseTech(data.get("techs"));
            project.parseLinks(data.get("links"));

        }

        project.saveProject(projectRepository);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
