package framework.ui.browser;

import framework.utils.Logger;
import framework.utils.enums.PropertiesEnum;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import framework.utils.ReadProperties;

import java.util.concurrent.TimeUnit;

public class Browser {
    private static WebDriver driver;
    private static final int WAIT_TIME = 3;
    private static final String BROWSER_NAME = ReadProperties.readFromConfig(PropertiesEnum.CONFIG, "browser");
    private static final String userName = ReadProperties.readFromConfig(PropertiesEnum.CONFIG, "username");
    private static final String password = ReadProperties.readFromConfig(PropertiesEnum.CONFIG, "password");
    private static final String url = ReadProperties.readFromConfig(PropertiesEnum.CONFIG, "url");

    public static WebDriver getBrowser() {
        if (driver == null) {
            driver = BrowserFactory.getBrowser(BROWSER_NAME);

        }
        return driver;
    }

    public static void goToUrlAndLogin() {
        getBrowser().get("http://" + userName + ":" + password + "@" + url);
    }

    public static void refreshPage() {
        Logger.info("Refreshing the page");
        getBrowser().navigate().refresh();
    }

    public static void maximize() {
        Logger.info("Full screen mode is on");
        getBrowser().manage().window().maximize();
    }

    public static void quit() {
        Logger.info("Browser closes");
        getBrowser().quit();
    }

    public static void navigateToPreviousPage() {
        Logger.info("Navigate to previous page");
        getBrowser().navigate().back();
    }

    public static void addCookie(String key, String value) {
        getBrowser().manage().addCookie(new Cookie(key, value));
    }

    public static void selectIFrameElement(String elementId) {
        Logger.info("Switch to iframe");
        getBrowser().switchTo().frame(elementId);
        getBrowser().manage().timeouts().implicitlyWait(WAIT_TIME, TimeUnit.SECONDS);
    }

    public static void switchToDefaultContent() {
        Logger.info("Switching to the default content");
        getBrowser().switchTo().defaultContent();
    }
}
