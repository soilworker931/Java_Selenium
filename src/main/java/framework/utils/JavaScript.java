package framework.utils;

import framework.ui.browser.Browser;
import org.openqa.selenium.JavascriptExecutor;

public class JavaScript {
    private static final JavascriptExecutor js = (JavascriptExecutor) Browser.getBrowser();

    public static void closePopUp() {
        js.executeScript(FileUtils.readFile("src/main/java/test/js/closePopup.js"));
    }
}