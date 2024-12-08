package com.student.util;

import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {

    private static volatile PropertyReader propInstance;

    private PropertyReader() {
    }

    public static PropertyReader getInstance() {
        if (propInstance == null) {
            propInstance = new PropertyReader();
        }
        return propInstance;
    }

    public String getProperty(String propertyName) {
        Properties prop = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (inputStream == null) {
                System.out.println("Property file not found in the classpath");
                return null;
            }
            prop.load(inputStream);
            String value = prop.getProperty(propertyName);
            if (value != null) {
                System.out.println("Property '{"+propertyName+"}' found with value: {"+value+"}");
            } else {
                System.out.println("Property '{"+propertyName+"}' not found in the config file");
            }
            return value;

        } catch (Exception e) {
            System.out.println("Exception occurred while reading property: " + propertyName);  // Log the exception with details
        }
        return null;
    }
}
