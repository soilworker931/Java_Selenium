package pages;

import framework.api.RepPortalApi;
import framework.ui.base.BaseForm;
import framework.ui.elements.Label;
import framework.utils.DateUtils;
import framework.utils.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.Date;

public class ProjectPage extends BaseForm {
    private Label testsQuantityPerPage = new Label(By.xpath("//tbody/tr/td[4]"), "test case");

    public ProjectPage() {
        super(By.xpath("//div[@class='panel-heading' and contains(text(), 'Total tests progress')]"), "Projects page");
    }

    public int getTestsQuantity() {
        return testsQuantityPerPage.getElements().size();
    }

    public ArrayList<Date> getCasesStartTimeOnThePage() {
        int testNumber = 2;
        ArrayList<Date>dateList = new ArrayList<>();
        for (int i = 0; i < getTestsQuantity(); i++) {
            Label caseDate = new Label(By.xpath(String.format("//tbody/tr[%d]/td[4]", testNumber)), String.format("test case: %d", i+1));
            dateList.add(DateUtils.stringToDate(caseDate.getElementText()));
            testNumber++;
        }
        return dateList;
    }

    public boolean descendingOrderTimeStartedTestsCheck() {
        Logger.info("Checking that test cases are in descending order by time");
        boolean check = true;
        ArrayList<Date> dateList = getCasesStartTimeOnThePage();
        for (int i = 1; i < dateList.size() - 1; i++) {
            if (dateList.get(i).before(dateList.get(i+1))) {
                check = false;
                break;
            }
        }
        return check;
    }

    public ArrayList<String> getCasesTitlesOnThePage() {
        int testNumber = 2;
        ArrayList<String> dateList = new ArrayList<>();
        for (int i = 0; i < getTestsQuantity() - 1; i++) {
            Label caseTitle = new Label(By.xpath(String.format("//tbody/tr[%d]/td[1]/a", testNumber)), String.format("test case: %d", i+1));
            dateList.add(caseTitle.getElementText());
            testNumber++;
        }
        return dateList;
    }

    public boolean checkAllCasesTitlesOnThePageExist(String projectId) {
        Logger.info("Checking that all test cases on the page exist");
        boolean check = false;
        ArrayList<String> pageCasesList = getCasesTitlesOnThePage();
        ArrayList<String> jsonCasesList = new ArrayList<>();
        JSONArray jsonArray = RepPortalApi.getProjectTests(projectId);
        for (int i = 1; i < jsonArray.length(); i++) {
            JSONObject caseTitle = jsonArray.getJSONObject(i);
            jsonCasesList.add(caseTitle.get("name").toString());
        }
        for (int i = 1; i < pageCasesList.size(); i++) {
            if (jsonCasesList.contains(pageCasesList.get(i))) {
                check = true;
            } else {
                break;
            }
        }
        return check;
    }
}
