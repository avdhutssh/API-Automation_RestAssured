package com.student.util;

import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssertionUtils {

    public static void verifyStatusCode(ValidatableResponse response, int statusCode) {
        response.statusCode(statusCode);
    }

    public static void verifyResponsePayload(ValidatableResponse response, String JsonPath, String expectedValue) {
        String responseBody = response.extract().body().asString();
        System.out.println(responseBody);
        JsonPath jp = new JsonPath(responseBody);
        Object actualValue = jp.get(JsonPath);
        assertEquals(expectedValue, actualValue, "Expected value doesn't match the actual value!");
    }
}
