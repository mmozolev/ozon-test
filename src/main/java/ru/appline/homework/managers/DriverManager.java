package ru.appline.homework.managers;

import org.apache.commons.exec.OS;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static ru.appline.homework.utils.PropertyConst.*;

public class DriverManager {

    private WebDriver webDriver;

    private static DriverManager INSTANCE = null;

    private static final PropertyManager properties = PropertyManager.getPropertyManager();

    private DriverManager() {
    }

    public WebDriver getWebDriver() {
        if (webDriver == null) {
            webDriver = initDriverByOs();
        }
        return webDriver;
    }

    public static DriverManager getDriverManager() {
        if (INSTANCE == null) {
            INSTANCE = new DriverManager();
        }
        return INSTANCE;
    }

    public void quitWebDriver() {
        if (webDriver != null) {
            webDriver.quit();
            webDriver = null;
        }
    }

    private WebDriver initDriverByOs() {
        if (OS.isFamilyWindows()) {
            return initDriver(PATH_GECKO_WEBDRIVER_WINDOWS, PATH_CHROME_WEBDRIVER_WINDOWS);
        } else if (OS.isFamilyUnix()) {
            return initDriver(PATH_GECKO_WEBDRIVER_LINUX, PATH_CHROME_WEBDRIVER_LINUX);
        } else if (OS.isFamilyMac()) {
            return initDriver(PATH_GECKO_WEBDRIVER_MAC, PATH_CHROME_WEBDRIVER_MAC);
        }
        Assertions.fail("Данная ОС не поддерижвается во фреймворке");
        return null;
    }


    private WebDriver initDriver(String gecko, String chrome) {
        switch (properties.getProperty(TYPE_BROWSER)) {
            case "firefox":
                System.setProperty("webdriver.gecko.driver", properties.getProperty(gecko));
                return new FirefoxDriver();
            case "chrome":
                System.setProperty("webdriver.chrome.driver", properties.getProperty(chrome));
                return new ChromeDriver();
            default:
                Assertions.fail("Типа браузера '" + properties.getProperty(TYPE_BROWSER) + "' не существует во фреймворке");
                return null;
        }
    }
}