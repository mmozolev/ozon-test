package ru.appline.homework.managers;

import java.util.concurrent.TimeUnit;

import static ru.appline.homework.utils.PropertyConst.*;

public class InitManager {

    private static final PropertyManager properties = PropertyManager.getPropertyManager();
    private static final DriverManager driverManager = DriverManager.getDriverManager();

    public static void initFramework() {
        driverManager.getWebDriver().manage().window().maximize();
        driverManager.getWebDriver().manage().timeouts().implicitlyWait(Integer.parseInt(properties.getProperty(IMPLICITLY_WAIT)), TimeUnit.SECONDS);
        driverManager.getWebDriver().manage().timeouts().pageLoadTimeout(Integer.parseInt(properties.getProperty(PAGE_LOAD_WAIT)), TimeUnit.SECONDS);
        driverManager.getWebDriver().get(PropertyManager.getPropertyManager().getProperty(APP_URL));
    }

    public static void quitFramework() {
        driverManager.quitWebDriver();
    }
}
