package framework.utils.enums;

public enum ProjectsEnum {
    NEXAGE ("Nexage", "1");

    String projectName;
    String projectId;

    ProjectsEnum(String projectName, String projectId) {
        this.projectName = projectName;
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getProjectId() {
        return projectId;
    }
}
