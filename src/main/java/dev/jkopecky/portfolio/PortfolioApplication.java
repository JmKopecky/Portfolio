package dev.jkopecky.portfolio;

import dev.jkopecky.portfolio.data.Project;
import dev.jkopecky.portfolio.data.ProjectRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PortfolioApplication {

    public static void main(String[] args) {
        SpringApplication.run(PortfolioApplication.class, args);
    }



    public static void createProjects(ProjectRepository projectRepository) {

        if (projectRepository.count() != 0) {
            return;
        }

        try {
            Project condelu = Project.createProject(
                    "Condelu Meals", projectRepository);
            condelu.initDefaults(
                    "A website for an imagined vegetarian restaurant with a focus on providing healthy food in an affordable and convenient manner.",
                    "https://placehold.co/400/000000/FFFFFF?text=Hello+World!&font=raleway",
                    "/",
                    "/projects/condelu");
            condelu.buildTechList("Java","HTML","CSS/SCSS","Javascript","Spring Boot","Thymeleaf");
            projectRepository.save(condelu);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        try {
            Project palaestra = Project.createProject(
                    "Palaestra Tournaments", projectRepository);
            palaestra.initDefaults(
                    "A web-based tournament hosting system that operates over LAN, allowing clients to easily compete through a browser interface.",
                    "https://placehold.co/400/000000/FFFFFF?text=Hello+World!&font=raleway",
                    "/",
                    "/projects/palaestratournaments");
            palaestra.buildTechList("Java","HTML","CSS/SCSS","Javascript","Spring Boot", "Websockets");
            projectRepository.save(palaestra);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        try {
            Project mathminds = Project.createProject(
                    "MathMinds Test Generation", projectRepository);
            mathminds.initDefaults(
                    "A java app that generates a custom test from a set of template questions, with fields randomized and answers auto-calculated.",
                    "https://placehold.co/400/000000/FFFFFF?text=Hello+World!&font=raleway",
                    "/",
                    "/projects/mathmindstestgen");
            mathminds.buildTechList("Java","HTML","JSON");
            projectRepository.save(mathminds);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        try {
            Project cacao = Project.createProject(
                    "Cacao Student Tools", projectRepository);
            cacao.initDefaults(
                    "An Android application built to make the lives of students easier with automatic assignment tracking and live GPA calculation.",
                    "https://placehold.co/400/000000/FFFFFF?text=Hello+World!&font=raleway",
                    "/",
                    "/projects/cacao");
            cacao.buildTechList("Java","XML","Android Studio");
            projectRepository.save(cacao);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
