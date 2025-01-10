package dev.jkopecky.portfolio;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.jkopecky.portfolio.data.Project;
import dev.jkopecky.portfolio.data.ProjectRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

@SpringBootApplication
public class PortfolioApplication {

    public static void main(String[] args) {
        SpringApplication.run(PortfolioApplication.class, args);
    }



    public static void createProjects(ProjectRepository projectRepository) {

        if (projectRepository.count() != 0) {
            return;
        }

        //load from files if present
        String path = System.getProperty("user.home") + "/jk_portfolio/";
        String projectPath = path + "projects/";
        String blogPath = path + "blogs/";


        //define default project templates
        ArrayList<Project> projectTemplates = new ArrayList<>();
        try {
            Project condelu = Project.createProject(
                    "Condelu Meals", projectRepository);
            condelu.initDefaults(
                    "A website for an imagined vegetarian restaurant with a focus on providing healthy food in an affordable and convenient manner.",
                    "https://placehold.co/400/000000/FFFFFF?text=Hello+World!&font=raleway",
                    "/",
                    "/projects/condelu");
            condelu.buildTechList("Java","HTML","CSS/SCSS","Javascript","Spring Boot","Thymeleaf");
            projectTemplates.add(condelu);
        } catch (Exception ignored) {}
        try {
            Project palaestra = Project.createProject(
                    "Palaestra Tournaments", projectRepository);
            palaestra.initDefaults(
                    "A web-based tournament hosting system that operates over LAN, allowing clients to easily compete through a browser interface.",
                    "https://placehold.co/400/000000/FFFFFF?text=Hello+World!&font=raleway",
                    "/",
                    "/projects/palaestratournaments");
            palaestra.buildTechList("Java","HTML","CSS/SCSS","Javascript","Spring Boot", "Websockets");
            projectTemplates.add(palaestra);
        } catch (Exception ignored) {}
        try {
            Project mathminds = Project.createProject(
                    "MathMinds Test Generation", projectRepository);
            mathminds.initDefaults(
                    "A java app that generates a custom test from a set of template questions, with fields randomized and answers auto-calculated.",
                    "https://placehold.co/400/000000/FFFFFF?text=Hello+World!&font=raleway",
                    "/",
                    "/projects/mathmindstestgen");
            mathminds.buildTechList("Java","HTML","JSON");
            projectTemplates.add(mathminds);
        } catch (Exception ignored) {}
        try {
            Project cacao = Project.createProject(
                    "Cacao Student Tools", projectRepository);
            cacao.initDefaults(
                    "An Android application built to make the lives of students easier with automatic assignment tracking and live GPA calculation.",
                    "https://placehold.co/400/000000/FFFFFF?text=Hello+World!&font=raleway",
                    "/",
                    "/projects/cacao");
            cacao.buildTechList("Java","XML","Android Studio");
            projectTemplates.add(cacao);
        } catch (Exception ignored) {}


        //ensure default project templates have associated files for data storage.
        for (Project project : projectTemplates) {
            try {
                //check if project already has file. If so, read it and create a project variable, which would be saved to database
                ObjectMapper mapper = new ObjectMapper();
                String defaultProjectPath = projectPath + project.getResourceName() + "/";
                Files.createDirectories(Paths.get(defaultProjectPath));
                defaultProjectPath += project.getResourceName() + ".json";
                File file = new File(defaultProjectPath);
                if (file.isFile()) {
                    //file exists. Create and save project from it instead.
                    project = mapper.readValue(file, Project.class);
                } else {
                    //project does not exist, create it.
                    mapper.writeValue(file, project);
                }
                projectRepository.save(project);
            } catch (Exception ignored) {}
        }

    }

}
