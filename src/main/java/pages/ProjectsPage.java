package pages;

import framework.ui.base.BaseForm;
import framework.ui.browser.Browser;
import framework.ui.elements.Button;
import framework.ui.elements.Label;
import framework.ui.elements.TextBox;
import framework.utils.Logger;
import org.openqa.selenium.By;

import java.util.ArrayList;

public class ProjectsPage extends BaseForm {
    private static final Label VARIANT_VERSION_BTN = new Label(By.xpath("//p[contains(@class, 'footer-text')]/span"), "Version number");
    private static final Button ADD_PROJECT_BTN = new Button(By.xpath("//button[@data-target='#addProject']"), "Add+ button");
    private static final TextBox ENTER_PROJECT_TBX = new TextBox(By.id("projectName"), "Enter project name field");
    private static final Button SUBMIT_PROJECT_BTN = new Button(By.xpath("//button[@type='submit']"), "Submit project");
    private static final String ADD_PROJECT_FRAME_ID = "addProjectFrame";
    private static final Label SUCCESS_MESSAGE = new Label(By.xpath("//div[contains(@class, 'alert-success')]"), "Success message");
    private static final Label PROJECTS = new Label(By.xpath("//a[@class='list-group-item']"), "Project");

    public ProjectsPage() {
        super(By.xpath("//div[@class='panel-heading' and contains(text(), 'Available projects')]"), "Projects page");
    }

    public String getFooterText() {
        return VARIANT_VERSION_BTN.getElementText();
    }

    public ArrayList<String> getProjectsList() {
        ArrayList<String> projectNames = new ArrayList<>();
        for (int i = 1; i < PROJECTS.getElements().size(); i++) {
            Label projectName = new Label
                    (By.xpath(String.format("//a[@class='list-group-item'][%s]", i)), String.format("project: %d", i));
            projectNames.add(projectName.getElementText());
        }
        return projectNames;
    }

    public boolean isProjectsExists(String projectName) {
        Logger.info("Checking that the project exists");
        Label project = new Label(By.xpath(String.format("//a[@class='list-group-item' and text() ='%s']", projectName)), projectName);
        return project.waitAndCheckIsDisplayed();
    }

    public void clickAddProjectBtn() {
        ADD_PROJECT_BTN.click();
    }

    public void enterProjectName(String projectName) {
        Logger.info("Entering project: " + projectName);
        Browser.selectIFrameElement(ADD_PROJECT_FRAME_ID);
        ENTER_PROJECT_TBX.typeText(projectName);
        Browser.switchToDefaultContent();
    }

    public void submitProjectName() {
        Browser.selectIFrameElement(ADD_PROJECT_FRAME_ID);
        SUBMIT_PROJECT_BTN.click();
        Browser.switchToDefaultContent();
    }


    public void createProject(String projectName) {
        Logger.info("Creating project: " + projectName);
        enterProjectName(projectName);
        submitProjectName();
    }

    public String getSuccessMessage() {
        Browser.selectIFrameElement(ADD_PROJECT_FRAME_ID);
        String message = SUCCESS_MESSAGE.getElement().getText();
        Browser.switchToDefaultContent();
        return message;
    }

    public void openProject(String projectName) {
        Logger.info("Opening project: " + projectName);
        String project = String.format("//a[@class='list-group-item' and text() = '%s']", projectName);
        new Button(By.xpath(project), String.format("project %s", projectName)).click();
    }

}
