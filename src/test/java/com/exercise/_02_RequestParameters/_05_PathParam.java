package com.exercise._02_RequestParameters;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

}
