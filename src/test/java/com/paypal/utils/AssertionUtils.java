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

    @Step("Verify response contains field: {1}")
    public static void verifyResponseContainsNotNullField(Response response, String path) {
        log.info("Verifying response contains field: {}", path);
        AllureReportUtils.logStep("Verifying response contains field: " + path);

        Assert.assertNotNull(response.jsonPath().get(path),
                "Response does not contain field: " + path);

        AllureReportUtils.logVerification("Response field verification passed: " + path);
        log.info("Response field verification passed: {}", path);
    }

    @Step("Verify field is non null: {1}")
    public static void verifyNotNullField(String value) {
        log.info("Verifying value is not null");
        AllureReportUtils.logStep("Verifying value is not null: ");

        Assert.assertNotNull(value,
                "Field does not contain data: ");

        AllureReportUtils.logVerification("Field contain data verification passed");
        log.info("Field contain data verification passed");
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

    @Step("Verify correct value: expected={1}")
    public static void verifyCorrectValue(Object actualValue, Object expectedValue) {
        verifyCorrectValue(actualValue, expectedValue, "Value verification");
    }

    @Step("Verify correct value: expected={1}")
    public static void verifyCorrectValue(Object actualValue, Object expectedValue, String message) {
        log.info("Verifying correct value: actual='{}', expected='{}'", actualValue, expectedValue);
        AllureReportUtils.logStep("Verifying value - Actual: '" + actualValue + "', Expected: '" + expectedValue + "'");

        Assert.assertEquals(actualValue, expectedValue,
                message + " - Expected '" + expectedValue + "' but got '" + actualValue + "'");

        AllureReportUtils.logVerification("Value verification passed: " + message);
        log.info("Value verification passed: {}", message);
    }

    @Step("Verify condition is true: {1}")
    public static void verifyCondition(boolean condition, String message) {
        log.info("Verifying condition: {}", message);
        AllureReportUtils.logStep("Verifying condition: " + message);

        Assert.assertTrue(condition, message);

        AllureReportUtils.logVerification("Condition verification passed: " + message);
        log.info("Condition verification passed: {}", message);
    }

    @Step("Verify integer value: {1}={2}")
    public static void verifyIntValue(Response response, String path, int expectedValue) {
        log.info("Verifying integer value at {}: expected {}", path, expectedValue);
        AllureReportUtils.logStep("Verifying integer value at '" + path + "' - Expected: " + expectedValue);

        int actualValue = response.jsonPath().getInt(path);
        Assert.assertEquals(actualValue, expectedValue,
                "Expected " + expectedValue + " at path '" + path + "' but got " + actualValue);

        AllureReportUtils.logVerification("Integer value verification passed for " + path);
        log.info("Integer value verification passed for {}", path);
    }

    @Step("Verify double value: {1}={2}")
    public static void verifyDoubleValue(Response response, String path, double expectedValue, double delta) {
        log.info("Verifying double value at {}: expected {} with delta {}", path, expectedValue, delta);
        AllureReportUtils.logStep("Verifying double value at '" + path + "' - Expected: " + expectedValue);

        double actualValue = response.jsonPath().getDouble(path);
        Assert.assertEquals(actualValue, expectedValue, delta,
                "Expected " + expectedValue + " (±" + delta + ") at path '" + path + "' but got " + actualValue);

        AllureReportUtils.logVerification("Double value verification passed for " + path);
        log.info("Double value verification passed for {}", path);
    }

    @Step("Verify boolean value: {1}={2}")
    public static void verifyBooleanValue(Response response, String path, boolean expectedValue) {
        log.info("Verifying boolean value at {}: expected {}", path, expectedValue);
        AllureReportUtils.logStep("Verifying boolean value at '" + path + "' - Expected: " + expectedValue);

        boolean actualValue = response.jsonPath().getBoolean(path);
        Assert.assertEquals(actualValue, expectedValue,
                "Expected " + expectedValue + " at path '" + path + "' but got " + actualValue);

        AllureReportUtils.logVerification("Boolean value verification passed for " + path);
        log.info("Boolean value verification passed for {}", path);
    }

    @Step("Verify response structure")
    public static <T> T verifyResponseStructure(Response response, Class<T> responseType) {
        log.info("Verifying response structure matches {}", responseType.getSimpleName());
        AllureReportUtils.logStep("Verifying response structure matches " + responseType.getSimpleName());

        T responseObject = null;
        try {
            responseObject = response.as(responseType);
            Assert.assertNotNull(responseObject, "Response could not be deserialized to " + responseType.getSimpleName());

            AllureReportUtils.logVerification("Response structure verification passed for " + responseType.getSimpleName());
            log.info("Response structure verification passed for {}", responseType.getSimpleName());

            return responseObject;
        } catch (Exception e) {
            log.error("Failed to deserialize response to {}: {}", responseType.getSimpleName(), e.getMessage());
            Assert.fail("Failed to deserialize response to " + responseType.getSimpleName() + ": " + e.getMessage());
            return null;
        }
    }

}