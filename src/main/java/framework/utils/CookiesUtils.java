package framework.utils;

import framework.ui.browser.Browser;
import org.openqa.selenium.Cookie;

public class CookiesUtils {
    public static void addCookie(String key, String value) {
        Browser.getBrowser().manage().addCookie(new Cookie(key, value));
    }
}
