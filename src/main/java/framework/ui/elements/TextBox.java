package framework.ui.elements;

import framework.ui.base.BaseElement;
import org.openqa.selenium.By;

public class TextBox extends BaseElement {
    public TextBox(By elementLocator, String elementName) {
        super(elementLocator, elementName);
    }

    public void typeText(String text) {
        getElement().sendKeys(text);
    }
}
