package framework.ui.browser;

import framework.utils.Logger;
import framework.utils.enums.PropertiesEnum;
import org.openqa.selenium.WebDriver;
import framework.utils.ReadProperties;

public class Browser {
    private static WebDriver driver;
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
}
