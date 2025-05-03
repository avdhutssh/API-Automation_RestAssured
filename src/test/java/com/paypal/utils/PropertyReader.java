package com.paypal.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {

    private static final Logger log = LogManager.getLogger(PropertyReader.class);
    private static final Properties prop = new Properties();
    private static final String CONFIG_FILE_PATH = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "application.properties";
    private static PropertyReader instance = null;

    private PropertyReader() {
        loadProperties();
    }

    public static synchronized PropertyReader getInstance() {
        if (instance == null) {
            instance = new PropertyReader();
        }
        return instance;
    }

    public String getProperty(String key) {
        return prop.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return prop.getProperty(key, defaultValue);
    }

    private void loadProperties() {
        try (FileInputStream fileInput = new FileInputStream(CONFIG_FILE_PATH)) {
            prop.load(fileInput);
            log.info("Successfully loaded properties from: {}", CONFIG_FILE_PATH);
        } catch (IOException e) {
            log.error("Failed to load properties file: {}", e.getMessage(), e);
        }
    }
}
