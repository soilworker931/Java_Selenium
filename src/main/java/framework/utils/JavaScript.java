package framework.utils;

import framework.ui.browser.Browser;
import org.openqa.selenium.JavascriptExecutor;

public class JavaScript {
    public static void closePopUp() {
        JavascriptExecutor js = (JavascriptExecutor) Browser.getBrowser();
        js.executeScript("$('#addProject').modal('hide')");
    }
}