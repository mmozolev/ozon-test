package ru.appline.homework.managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyManager {

    private final Properties properties = new Properties();

    private static PropertyManager propertyManager = null;

    private PropertyManager() {
        loadAppProperties();
        loadCustomProperties();
    }

    public static PropertyManager getPropertyManager() {
        if(propertyManager == null) {
            propertyManager = new PropertyManager();
        }
        return propertyManager;
    }

    private void loadAppProperties() {
        try {
            properties.load(new FileInputStream(
                    new File("src/main/resources/" +
                            System.getProperty("propFile", "environment") + ".properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCustomProperties() {
        properties.forEach((key, value) -> System.getProperties()
                .forEach((customKey, customValue) -> {
                    if (key.toString().equals(customKey.toString()) &&
                            !value.toString().equals(customValue.toString())) {
                        properties.setProperty(key.toString(), customValue.toString());
                    }
                }));
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

}
