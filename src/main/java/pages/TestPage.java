package pages;

import framework.ui.base.BaseForm;
import framework.ui.elements.Label;
import framework.utils.Logger;
import org.openqa.selenium.By;

public class TestPage extends BaseForm {
    private static final Label ATTACHMENT_IMAGE = new Label(By.xpath("//table[@class='table']//td[contains(text(), 'image/png')]"), "Image/png file");
    private static final Label LOGS_TEXT = new Label(By.xpath("//div[./div[@class='panel-heading' and text() = 'Logs']]//td"), "Logs");
    private static final Label PROJECT_NAME = new Label(By.xpath(
            "//div[./h4[@class='list-group-item-heading' and text() = 'Project name']]/p[@class='list-group-item-text']"), "Project name");
    private static final Label TEST_CASE = new Label(By.xpath(
            "//div[./h4[@class='list-group-item-heading' and text() = 'Test name']]/p[@class='list-group-item-text']"), "Test case number");
    private static final Label TEST_CASE_METHOD_NAME = new Label
            (By.xpath("//div[./h4[@class='list-group-item-heading' and text() = 'Test method name']]/p[@class='list-group-item-text']"), "Test case method name");
    private static final Label TEST_CASE_START_TIME = new Label
            (By.xpath("//p[@class='list-group-item-text' and contains(text(), 'Start time')]"), "Test case start time");
    private static final Label TEST_CASE_END_TIME = new Label
            (By.xpath("//p[@class='list-group-item-text' and contains(text(), 'End time')]"), "Test case start time");
    private static final Label TEST_CASE_ENVIRONMENT = new Label
            (By.xpath("//div[./h4[@class='list-group-item-heading' and text() = 'Environment']]/p[@class='list-group-item-text']"), "Text case environment");
    private static final Label TEST_CASE_BROWSER = new Label
            (By.xpath("//div[./h4[@class='list-group-item-heading' and text() = 'Browser']]/p[@class='list-group-item-text']"), "Text case environment");

    public TestPage() {
        super(By.xpath("//div[@class='panel-heading' and contains(text(), 'Fail Reason Info')]"), "Common info");
    }

    public boolean logsAreAddedCheck() {
        Logger.info("Checking that the logs are added");
        return LOGS_TEXT.isDisplayed();
    }

    public boolean attachmentIsAddedCheck() {
        Logger.info("Checking that the attachment is added");
        return ATTACHMENT_IMAGE.isDisplayed();
    }

    public String getProjectName() {
        Logger.info("Getting the project name");
        return PROJECT_NAME.getElement().getText();
    }

    public String getTestCaseName() {
        Logger.info("Getting test case name");
        return TEST_CASE.getElement().getText();
    }

    public String getTestCaseMethodName() {
        Logger.info("Checking test case method name");
        return TEST_CASE_METHOD_NAME.getElement().getText();
    }

    public String getTestCaseStartTime() {
        Logger.info("Getting test case start time");
        return TEST_CASE_START_TIME.getElement().getText();
    }

    public String getTestCaseEndTime() {
        Logger.info("Getting test case end time");
        return TEST_CASE_END_TIME.getElement().getText();
    }

    public String getTestCaseEnvironment() {
        Logger.info("Getting test case environment");
        return TEST_CASE_ENVIRONMENT.getElement().getText();
    }

    public String getTestCaseBrowser() {
        Logger.info("Getting test case browser");
        return TEST_CASE_BROWSER.getElement().getText();
    }
}
