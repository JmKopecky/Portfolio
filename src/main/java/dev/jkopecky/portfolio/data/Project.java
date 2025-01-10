package dev.jkopecky.portfolio.data;

import jakarta.persistence.*;
import org.thymeleaf.exceptions.AlreadyInitializedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String title;
    private String shortDesc;
    private String imageUrl;
    private String fileLocation;
    private String projectUrl;
    @ElementCollection
    private List<String> techList;


    public static Project createProject(String title, ProjectRepository projectRepository) throws Exception {
        Project project = new Project();
        project.title = title;

        for (Project p : projectRepository.findAll()) {
            if (p.title.equals(project.title)) {
                throw new Exception("project already exists");
            }
        }
        return project;
    }

    public void initDefaults(String shortDesc, String imageUrl, String fileLocation, String projectUrl) {
        this.shortDesc = shortDesc;
        this.imageUrl = imageUrl;
        this.fileLocation = fileLocation;
        this.projectUrl = projectUrl;
    }

    public void buildTechList(String... techs) {
        techList = new ArrayList<>();
        techList.addAll(Arrays.asList(techs));
    }






    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public String getProjectUrl() {
        return projectUrl;
    }

    public void setProjectUrl(String projectUrl) {
        this.projectUrl = projectUrl;
    }

    public List<String> getTechList() {
        return techList;
    }

    public void setTechList(List<String> techList) {
        this.techList = techList;
    }
}
