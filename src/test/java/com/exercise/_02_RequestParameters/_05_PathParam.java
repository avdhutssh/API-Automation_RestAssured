package com.exercise._02_RequestParameters;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class _05_PathParam {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "http://localhost:8085";
        RestAssured.basePath = "/student";
    }

    @DisplayName("Getting student information using Path parameter one by one")
    @Test
    public void _01_PP_OneByOne() {
        given()
                .pathParam("id", 1)
                .log()
                .all()
                .when()
                .get("/{id}")
                .then()
                .log()
                .all()
                .statusCode(200)
                .body("firstName", equalTo("Vernon"));
    }

    @DisplayName("Getting student information by Path parameter Using String Formatting")
    @Test
    public void _02_PP_StringFormatting() {
        given()
                .log()
                .all()
                .when()
                .get("/1")
                .then()
                .log()
                .all()
                .statusCode(200)
                .body("firstName", equalTo("Vernon"));
    }

    @DisplayName("Getting student information using Path parameter Map")
    @Test
    public void _03_PP_Map() {
        Map<String, Object> requestPP = new HashMap<>();
        requestPP.put("id", 1);
        given()
                .pathParams(requestPP)
                .log()
                .all()
                .when()
                .get("/{id}")
                .then()
                .log()
                .all()
                .statusCode(200)
                .body("firstName", equalTo("Vernon"));
    }
}
