package dev.jkopecky.portfolio.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.jkopecky.portfolio.data.ProjectRepository;
import dev.jkopecky.portfolio.data.SessionToken;
import dev.jkopecky.portfolio.data.SessionTokenRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminPanelController {



    ProjectRepository projectRepository;
    SessionTokenRepository sessionTokenRepository;
    public AdminPanelController(ProjectRepository projectRepository, SessionTokenRepository sessionTokenRepository) {
        this.projectRepository = projectRepository;
        this.sessionTokenRepository = sessionTokenRepository;
    }



    @GetMapping("/admin/panel")
    public String adminPanel(Model model, @CookieValue(value = "sessiontoken", defaultValue = "null") String sessionToken) {

        //auth check
        boolean authenticated = false;
        for (SessionToken token : sessionTokenRepository.findAll()) {
            if (token.getToken().equals(sessionToken)) {
                authenticated = true;
            }
        }
        if (!authenticated) {
            return "redirect:/admin/signon";
        }

        System.out.println("hi");

        model.addAttribute("projects", projectRepository.findAll());
        return "adminpanel";
    }


    @GetMapping("/admin/signon")
    public String adminSignon() {
        return "adminsignon";
    }


    @PostMapping("/admin/signon")
    public ResponseEntity<String> signon(@RequestBody String data, HttpServletResponse response) {
        //kill all old tokens.
        sessionTokenRepository.deleteAll();

        String username;
        String password;
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(data);
            username = node.get("username").asText();
            password = node.get("password").asText();
        } catch (Exception e) {
            return new ResponseEntity<>("unauthenticated", HttpStatus.OK);
        }

        if (!username.equals(System.getenv("PORTFOLIO_USR"))) {
            //todo: log failed attempts.
            System.out.println("Failed: " + username);
            return new ResponseEntity<>("unauthenticated", HttpStatus.OK);
        }
        if (!password.equals(System.getenv("PORTFOLIO_PW"))) {
            //todo: log failed attempts.
            System.out.println("Failed: " + password);
            return new ResponseEntity<>("unauthenticated", HttpStatus.OK);
        }

        //create token and return it.
        SessionToken token = SessionToken.createToken(sessionTokenRepository);
        Cookie cookie = new Cookie("sessiontoken", token.getToken());
        cookie.setMaxAge(60*60);
        response.addCookie(cookie);
        String toAdd = "sessiontoken=" + token.getToken() + "; Max-Age=3600;";
        HttpHeaders cookieHeaders = new HttpHeaders();
        cookieHeaders.add("Set-Cookie", toAdd);
        return new ResponseEntity<>("authenticated", cookieHeaders, HttpStatus.OK);
    }
}
