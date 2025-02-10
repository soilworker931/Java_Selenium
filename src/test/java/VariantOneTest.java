import framework.api.RepPortalApi;
import framework.sql.SQL;
import framework.testrail.TestRailUtils;
import framework.testrail.gurock.APIException;
import framework.ui.browser.Browser;
import framework.utils.*;
import framework.utils.enums.ProjectsEnum;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import pages.ProjectPage;
import pages.ProjectsPage;
import pages.TestPage;
import pages.TestsPage;
import pages.objects.SQLTestCase;

import java.sql.SQLException;

public class VariantOneTest {
    private static final String CASE_ID = "11345480";
    private static final String COOKIES_KEY = "token";
    private static final String FOOTER_TEXT = "Version: 1";
    private static final String PROJECT_NAME = StringGenerator.textGeneration(10);
    private static final String SUCCESS_MESSAGE = String.format("Project %s saved", PROJECT_NAME);
    private static TestRailUtils testRailUtils = new TestRailUtils();

    @BeforeSuite
    public static void createTestRun() {
        testRailUtils.addRun();
        testRailUtils.signIn();
    }

    @BeforeMethod
    public static void beforeRun() {
        Browser.getBrowser();
        Browser.maximize();
    }

    @Test
    public static void run() throws SQLException {
        ProjectsPage projectsPage = new ProjectsPage();
        ProjectPage nexage = new ProjectPage();
        ProjectPage newProject = new ProjectPage();
        TestsPage testsPage = new TestsPage();
        TestPage testPage = new TestPage();
        SQLTestCase testCase = new SQLTestCase();
        SQL sql = new SQL();

        Logger.logStep(1, "Add cookie");
        Browser.goToUrlAndLogin();
        Assert.assertTrue(projectsPage.pageIsDisplayed(), "Projects page is not displayed");
        Browser.addCookie(COOKIES_KEY, RepPortalApi.getToken());
        Browser.refreshPage();
        Assert.assertTrue(projectsPage.pageIsDisplayed(), "Projects page is not displayed");
        Assert.assertEquals(projectsPage.getFooterText(), FOOTER_TEXT, "Incorrect footer variant is displayed");

        Logger.logStep(2, "Check nexage first page cases list");
        projectsPage.openProject(ProjectsEnum.NEXAGE.getProjectName());
        Assert.assertTrue(nexage.pageIsDisplayed());
        Assert.assertTrue(nexage.descendingOrderTimeStartedTestsCheck(), "Tests are not in descending order");
        Assert.assertTrue(nexage.checkAllCasesTitlesOnThePageExist(ProjectsEnum.NEXAGE.getProjectId()), "Not all the cases on the page exist in JSON");

        Logger.logStep(3, "Add new project");
        Browser.navigateToPreviousPage();
        Assert.assertTrue(projectsPage.pageIsDisplayed());
        projectsPage.clickAddProjectBtn();
        projectsPage.createProject(PROJECT_NAME);
        Assert.assertEquals(projectsPage.getSuccessMessage(), SUCCESS_MESSAGE, "Incorrect message is shown");
        JavaScript.closePopUp();
        Browser.refreshPage();
        Assert.assertTrue(projectsPage.isProjectsExists(PROJECT_NAME), "The project was not created");

        Logger.logStep(4, "Adding test via DB");
        projectsPage.openProject(PROJECT_NAME);
        Assert.assertTrue(newProject.pageIsDisplayed());

        RobotUtil.takeScreenshot("attachment.png");
        sql.connectToDataBase();
        try {
            sql.fillTheResults(testCase, PROJECT_NAME, "attachment.png");
        } finally {
            sql.closeDataBase();
        }
        testsPage.isTestCaseAdded(testCase.getTestName());

        Logger.logStep(5, "Check all fields are correct for the case");
        testsPage.openCase(testCase.getTestName());
        Assert.assertTrue(testPage.pageIsDisplayed());
        Assert.assertEquals(testPage.getProjectName(), PROJECT_NAME, "Incorrect project name is displayed");
        Assert.assertEquals(testPage.getTestCaseName(), testCase.getTestName());
        Assert.assertEquals(testPage.getTestCaseMethodName(), testCase.getMethodName());
        Assert.assertTrue(testPage.getTestCaseStartTime().contains(testCase.getStartTime()));
        Assert.assertTrue(testPage.getTestCaseEndTime().contains(testCase.getEndTime()));
        Assert.assertEquals(testPage.getTestCaseEnvironment(), testCase.getEnv());
        Assert.assertEquals(testPage.getTestCaseBrowser(), testCase.getBrowser());
        Assert.assertTrue(testPage.attachmentIsAddedCheck("attachment.png"), "Attachment was not added");
        Assert.assertTrue(testPage.logsAreAddedCheck(), "Incorrect logs are provided");
    }

    @AfterMethod
    public void afterRun(ITestResult result) throws APIException {
        RobotUtil.takeScreenshot("testEnd.png");
        testRailUtils.setResultForCase(CASE_ID, TestResult.getId(result), TestResult.getComment(result));
        testRailUtils.setAttachmentToResult(CASE_ID, "testEnd");
        Browser.quit();
    }
}