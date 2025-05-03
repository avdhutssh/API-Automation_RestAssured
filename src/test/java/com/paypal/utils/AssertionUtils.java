package com.paypal.utils;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class AssertionUtils {
    private static final Logger log = LogManager.getLogger(AssertionUtils.class);

    private AssertionUtils() {
    }

    @Step("Verify status code: expected={1}")
    public static void verifyStatusCode(Response response, int statusCode) {
        log.info("Verifying status code: {}", statusCode);
        int actualStatusCode = response.getStatusCode();

        AllureReportUtils.logStep("Verifying status code - Expected: " + statusCode + ", Actual: " + actualStatusCode);

        Assert.assertEquals(actualStatusCode, statusCode,
                "Expected status code " + statusCode + " but got " + actualStatusCode);

        AllureReportUtils.logVerification("Status code verification passed: " + statusCode);
        log.info("Status code verification passed: {}", statusCode);
    }

    @Step("Verify and get value from response field: {1}")
    public static <T> T verifyAndGetResponseValue(Response response, String path, Class<T> valueType) {
        log.info("Verifying response contains non-null field and getting value: {}", path);
        AllureReportUtils.logStep("Verifying and extracting value from field: " + path);

        T value = response.jsonPath().get(path);

        Assert.assertNotNull(value, "Response field should not be null: " + path);

        AllureReportUtils.logVerification("Response field verification passed and value extracted: " + path);
        log.info("Response field verification passed. Value: {}", value);

        return value;
    }

    @Step("Verify string value: {1}={2}")
    public static void verifyStringValue(Response response, String path, String expectedValue) {
        log.info("Verifying string value at {}: expected '{}'", path, expectedValue);
        AllureReportUtils.logStep("Verifying string value at '" + path + "' - Expected: '" + expectedValue + "'");

        String actualValue = response.jsonPath().getString(path);
        Assert.assertEquals(actualValue, expectedValue,
                "Expected '" + expectedValue + "' at path '" + path + "' but got '" + actualValue + "'");

        AllureReportUtils.logVerification("String value verification passed for " + path);
        log.info("String value verification passed for {}", path);
    }


}