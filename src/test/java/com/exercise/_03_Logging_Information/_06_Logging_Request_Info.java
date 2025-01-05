package com.exercise._03_Logging_Information;

import io.qameta.allure.Description;

import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class _06_Logging_Request_Info {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8085;
        RestAssured.basePath = "/student";
    }

    @Description("This test will print out all the request headers")
    @Test
    public void _01_Print_AllRequestHeaders() {
        given()
                .log()
                .headers()
                .when()
                .get("/list")
                .then()
                .statusCode(200);
    }
}
