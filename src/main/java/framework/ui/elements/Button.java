package framework.ui.elements;

import framework.ui.base.BaseElement;
import org.openqa.selenium.By;

public class Button extends BaseElement {
    public Button(By locator, String buttonName) {
        super(locator, buttonName);
    }
}