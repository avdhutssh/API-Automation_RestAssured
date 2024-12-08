package com.student.util;

import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;

import java.util.Optional;

public class TestUtils {

    public static String getJsonPayloadValue(ValidatableResponse response, String JsonPath) {
        String responseBody = response.extract().body().asString();
        System.out.println(responseBody);
        JsonPath jp = new JsonPath(responseBody);
        Object value = jp.get(JsonPath);
        System.out.println(Optional.ofNullable(value));
        return Optional.ofNullable(value).map(Object::toString).orElse(null);
    }
}
