package framework.ui.browser;

import framework.utils.enums.PropertiesEnum;
import io.github.bonigarcia.wdm.WebDriverManager;
import framework.utils.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import framework.utils.ReadProperties;

import java.util.HashMap;

public class BrowserFactory {
    public static WebDriver getBrowser(String browserName) {
        String language = ReadProperties.readFromConfig(PropertiesEnum.CONFIG, "language");
        browserName = browserName.toLowerCase();
        WebDriver driver = null;
        switch (browserName) {
            case "chrome":
                Logger.info("Chosen browser is chrome");
                driver = getChromeInstance(language);
                break;
            case "firefox":
                Logger.info("Chosen browser is firefox");
                driver = getFirefoxInstance(language);
                break;
            default:
                throw new RuntimeException("the browser you chose does not exist");
        }
        return driver;
    }

    private static ChromeDriver getChromeInstance(String language) {
        WebDriverManager.chromedriver().setup();
        HashMap<String, Object> chromePrefs = new HashMap<>();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("safebrowsing.enabled", true);
        chromePrefs.put("intl.accept_languages", language);
        chromeOptions.setExperimentalOption("prefs", chromePrefs);
        return new ChromeDriver(chromeOptions);
    }

    private static FirefoxDriver getFirefoxInstance(String language) {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addPreference("browser.download.folderList", 2);
        firefoxOptions.addPreference("browser.download.useDownloadDir", true);
        firefoxOptions.addPreference("browser.helperApps.neverAsk.saveToDisk", "application/x-debian-package");
        firefoxOptions.addPreference("pdfjs.disabled", true);
        firefoxOptions.addPreference("intl.accept_languages", language);
        return new FirefoxDriver(firefoxOptions);
    }
}
