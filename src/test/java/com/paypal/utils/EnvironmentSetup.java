package com.paypal.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class EnvironmentSetup {

    public static void setupEnvironment(String env) {
        Properties props = new Properties();

        switch (env.toLowerCase()) {
            case "sandbox":
                props.setProperty("base.url", "https://api-m.sandbox.paypal.com");
                props.setProperty("client.id", "your-sandbox-client-id");
                props.setProperty("client.secret", "your-sandbox-client-secret");
                break;
            case "stage":
                props.setProperty("base.url", "https://api-m.stage.paypal.com");
                props.setProperty("client.id", "your-stage-client-id");
                props.setProperty("client.secret", "your-stage-client-secret");
                break;
            case "production":
                props.setProperty("base.url", "https://api-m.paypal.com");
                props.setProperty("client.id", "your-production-client-id");
                props.setProperty("client.secret", "your-production-client-secret");
                break;
            default:
                throw new IllegalArgumentException("Unsupported environment: " + env);
        }

        try (FileOutputStream out = new FileOutputStream("src/test/resources/config/current.properties")) {
            props.store(out, "Environment: " + env);
        } catch (IOException e) {
            LogUtils.error("Failed to write environment properties", e);
        }
    }
}