package com.student.tests;


import com.student.util.PropertyReader;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.util.Optional;

@ExtendWith(BaseTest.TestResultLogger.class)  // Attach the TestWatcher
public class BaseTest {

    public static PropertyReader prop;

    @BeforeAll
    public static void init() {
        prop = PropertyReader.getInstance();
        RestAssured.baseURI = prop.getProperty("baseUrl");
        RestAssured.port = Integer.valueOf(prop.getProperty("port"));
    }

    // Implement the TestWatcher as a static class
    public static class TestResultLogger implements TestWatcher {

        @Override
        public void testSuccessful(ExtensionContext context) {
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            System.out.println(context.getDisplayName().toUpperCase() + " SUCCESSFUL");
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        }

        @Override
        public void testFailed(ExtensionContext context, Throwable cause) {
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            System.err.println("Test Failed : " + context.getDisplayName().toUpperCase());
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        }

        @Override
        public void testDisabled(ExtensionContext context, Optional<String> reason) {
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            System.out.println("Test Disabled: " + context.getDisplayName().toUpperCase());
            reason.ifPresent(r -> System.out.println("Reason: " + r));
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        }

        @Override
        public void testAborted(ExtensionContext context, Throwable cause) {
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            System.err.println("Test Aborted: " + context.getDisplayName().toUpperCase());
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        }
    }
}
