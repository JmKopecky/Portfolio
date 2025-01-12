package dev.jkopecky.portfolio.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.jkopecky.portfolio.PortfolioApplication;
import jakarta.persistence.*;
import org.thymeleaf.exceptions.AlreadyInitializedException;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String displayName;
    private String title;
    private String shortDesc;
    private String imageUrl;
    private String fileLocation;
    private String projectUrl;
    @ElementCollection
    private List<String> techList;
    private String date;



    public static Project createProject(String title, ProjectRepository projectRepository) throws Exception {
        Project project = new Project();
        project.displayName = title;
        project.title = toResource(title);

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
        Date creationDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        this.date = formatter.format(creationDate);
    }

    public void buildTechList(String... techs) {
        techList = new ArrayList<>();
        techList.addAll(Arrays.asList(techs));
    }

    public static String toResource(String title) {
        String trim = title.toLowerCase().trim();
        String whiteSpaceReplaced = "";
        for (int i = 0; i < trim.length(); i++) {
            String character = "" + trim.charAt(i);
            if (!character.matches("[a-z0-9]")) {
                whiteSpaceReplaced += "_";
            } else {
                whiteSpaceReplaced += character;
            }
        }
        return whiteSpaceReplaced;
    }

    public String retrieveHTML() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String target = PortfolioApplication.projectPath + toResource(title) + "/" + toResource(title) + "_page.txt";
            File file = new File(target);
            if (file.isFile()) {
                return Files.readString(file.toPath());
            } else {
                //project does not exist, create it.
                return "file does not exist";
            }
        } catch (Exception e) {
            return "exception encountered retrieving project (" + toResource(title) + ") html";
        }
    }


    public static boolean exists(String title, ProjectRepository projectRepository) {
        for (Project p : projectRepository.findAll()) {
            if (p.title.equals(title)) {
                return true;
            }
        }
        return false;
    }


    public boolean writeHTML(String html) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String target = PortfolioApplication.projectPath + toResource(title) + "/" + toResource(title) + "_page.txt";
            File file = new File(target);
            if (file.isFile()) {
                Files.write(Paths.get(target), html.getBytes());
                return true;
            } else {
                //project does not exist, create it.
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean parseTech(String techs) {
        List<String> newTech = new ArrayList<>();
        try {
            newTech.addAll(Arrays.asList(techs.split("\n")));
        } catch (Exception e) {
            return false;
        }
        this.setTechList(newTech);
        return true;
    }


    public boolean saveProject(ProjectRepository projectRepository) {
        String projectPath = PortfolioApplication.projectPath;
        try {
            //check if project already has file. If so, read it and create a project variable, which would be saved to database
            ObjectMapper mapper = new ObjectMapper();
            String defaultProjectPath = projectPath + Project.toResource(getTitle()) + "/";
            Files.createDirectories(Paths.get(defaultProjectPath));
            String jsonPath = defaultProjectPath + Project.toResource(getTitle()) + ".json";
            File json = new File(jsonPath);
            if (json.isFile()) {
                //file exists. Create and save project from it instead.
                //todo: replace with: project = mapper.readValue(json, Project.class);
                mapper.writeValue(json, this);
            } else {
                //project does not exist, create it.
                mapper.writeValue(json, this);
            }
            String htmlPath = defaultProjectPath + Project.toResource(getTitle()) + "_page.txt";
            File page = new File(htmlPath);
            if (!page.isFile()) {
                page.createNewFile();
            }
            projectRepository.save(this);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
