package dev.jkopecky.portfolio.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ContactController {

    @GetMapping("/contact")
    public String contact(Model model) {
        return "contact";
    }


    @PostMapping("/contact")
    public ResponseEntity<Object> receiveContactForm(@RequestBody String body) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(body);
            String name = node.get("name").asText();
            String email = node.get("email").asText();
            String message = node.get("message").asText();
            //todo implement form logic
        } catch (Exception e) {
            //todo implement proper logging
            System.out.println(e);
            return new ResponseEntity<>("error", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
