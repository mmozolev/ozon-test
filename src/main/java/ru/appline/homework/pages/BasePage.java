package ru.appline.homework.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.appline.homework.managers.DriverManager;
import ru.appline.homework.managers.PageManager;
import ru.appline.homework.managers.PropertyManager;
import ru.appline.homework.utils.Cart;

import java.util.concurrent.TimeUnit;

import static ru.appline.homework.utils.PropertyConst.IMPLICITLY_WAIT;

public class BasePage {

    @FindBy (xpath = "//input[@placeholder='Искать на Ozon']")
    WebElement searchLine;

    protected DriverManager driverManager = DriverManager.getDriverManager();

    protected PageManager app = PageManager.getPageManager();

    protected WebDriverWait wait = new WebDriverWait(driverManager.getWebDriver(), 15, 1000 );

    protected Actions actions = new Actions(driverManager.getWebDriver());

    protected JavascriptExecutor js = (JavascriptExecutor) driverManager.getWebDriver();

    protected Cart cart = Cart.getCart();

    public BasePage() {
        PageFactory.initElements(driverManager.getWebDriver(), this);
    }

    protected void scrollToElement(WebElement element){
        js.executeScript("arguments[0].scrollIntoView(false);", element);
    }

    protected void scrollToElementWithOffSet(WebElement element, int x, int y) {
        String code = "windows.scroll(" + (element.getLocation().x + x) + "," +
                + (element.getLocation().y + y) + ");";
        js.executeScript(code, element, x, y);
    }

    protected void jsClickOnElement(WebElement element) {
        js.executeScript("arguments[0];", element);
    }

    protected WebElement elementIsClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void elementIsVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void fillFieldBase(WebElement element,String value) {
        scrollToElement(element);
        element.sendKeys(value);
    }

    public boolean isElementExist(WebElement element, String xpath) {
        boolean flag = false;
        try {
            DriverManager.getDriverManager().getWebDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            element.findElement(By.xpath(xpath));
            flag = true;
        } catch (NoSuchElementException ignore) {
        } finally {
            DriverManager.getDriverManager().getWebDriver().manage().timeouts().implicitlyWait(Integer.parseInt(PropertyManager.getPropertyManager().getProperty(IMPLICITLY_WAIT)), TimeUnit.SECONDS);
        }
        return flag;
    }

    public boolean isElementExist(WebElement element) {
        boolean flag = false;
        try {
            DriverManager.getDriverManager().getWebDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            scrollToElement(element);
            flag = true;
        } catch (NoSuchElementException ignore) {
        } finally {
            DriverManager.getDriverManager().getWebDriver().manage().timeouts().implicitlyWait(Integer.parseInt(PropertyManager.getPropertyManager().getProperty(IMPLICITLY_WAIT)), TimeUnit.SECONDS);
        }
        return flag;
    }

    protected void checkField(WebElement element, String value, String text) {
        Assertions.assertEquals(element.getText(), value,
                "Значение поля " + text + " не равно " + value);
    }


}
