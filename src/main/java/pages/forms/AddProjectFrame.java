package pages.forms;

import framework.ui.base.BaseElement;
import framework.ui.browser.Browser;
import framework.utils.Logger;
import org.openqa.selenium.By;

import java.util.concurrent.TimeUnit;

public class AddProjectFrame extends BaseElement {
    public AddProjectFrame(By locator, String buttonName) {
        super(locator, buttonName);
    }

    private static final int WAIT_TIME = 3;

    public void selectIFrameElement(String elementId) {
        Logger.info("Switch to iframe");
        Browser.getBrowser().switchTo().frame(elementId);
        Browser.getBrowser().manage().timeouts().implicitlyWait(WAIT_TIME, TimeUnit.SECONDS);
    }

    public void switchToDefaultContent() {
        Logger.info("Switching to the default content");
        Browser.getBrowser().switchTo().defaultContent();
    }
}
