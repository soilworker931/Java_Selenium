package pages;

import framework.ui.base.BaseForm;
import framework.ui.elements.Button;
import framework.ui.elements.Label;
import org.openqa.selenium.By;

public class TestsPage extends BaseForm {

    public TestsPage() {
        super(By.xpath("//div[@class='panel-heading' and contains(text(), 'Total tests progress')]"), "Total tests progress");
    }

    public void openCase(String caseName) {
        Button testCase = new Button(By.xpath(String.format("//a[contains(text(),'%s')]", caseName)), String.format("test case: %s", caseName));
        testCase.click();
    }

    public boolean isTestCaseAdded(String caseName) {
        Label testCase = new Label (By.xpath(String.format("//a[contains(text(),'%s')]", caseName)), "Case name");
        return testCase.waitAndCheckIsDisplayed();
    }
}
