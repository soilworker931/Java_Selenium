package framework.ui.base;

import framework.ui.elements.Label;
import org.openqa.selenium.By;

public abstract class BaseForm {
    protected final By locator;
    protected final String name;

    public BaseForm(By locator, String name) {
        this.locator = locator;
        this.name = name;
    }

    public boolean pageIsDisplayed() {
        return new Label(locator, name).isDisplayed();
    }
}
