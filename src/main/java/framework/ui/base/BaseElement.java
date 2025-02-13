package framework.ui.base;

import framework.ui.browser.Browser;
import framework.utils.Logger;
import framework.utils.ReadProperties;
import framework.utils.enums.PropertiesEnum;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public abstract class BaseElement {
    protected By elementLocator;
    protected String elementName;
    private static final int TIMEOUT_INTERVAL = Integer.valueOf(ReadProperties.readFromConfig(PropertiesEnum.CONFIG, "timeout"));

    public BaseElement(By elementLocator, String elementName) {
        this.elementLocator = elementLocator;
        this.elementName = elementName;
    }

    public WebElement getElement() {
        Logger.info("get element " + this.elementName);
        return Browser.getBrowser().findElement(elementLocator);
    }

    public String getElementText() {
        Logger.info("get element text " + this.elementName);
        return Browser.getBrowser().findElement(elementLocator).getText();
    }

    public void click() {
        Logger.info(this.elementName + " click");
        getElement().click();
    }

    public List<WebElement> getElements() {
        Logger.info("get elements " + this.elementName);
        return Browser.getBrowser().findElements(this.elementLocator);
    }

    public boolean isDisplayed() {
        Logger.info(this.elementName + " check");
        return getElements().size() > 0;
    }

    public boolean waitAndCheckIsDisplayed() {
        Logger.info(this.elementName + " check");
        WebDriverWait wait = new WebDriverWait(Browser.getBrowser(), TIMEOUT_INTERVAL);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(this.elementLocator));
        return element.isDisplayed();
    }
}