package com.exercise._03_Logging_Information;

import io.qameta.allure.Description;
import io.restassured.RestAssured;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;


import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class _07_Logging_Response_Info {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8085;
        RestAssured.basePath = "/student";
    }

    @Description("This test will print out all the Response Headers")
    @Test
    public void _01_Print_AllResponseHeaders() {
        given()
                .when()
                .get("/list")
                .then()
                .statusCode(200)
                .log()
                .headers();
    }

    @Description("This test will print out Response Status Line")
    @Test
    public void _02_Print_ResponseStatusLine() {
        given()
                .when()
                .get("/list")
                .then()
                .statusCode(200)
                .log()
                .status()
                .statusLine(containsString("HTTP/1.1 200"));

        // Method 1: Using extract()
        String statusLine1 = given()
                .when()
                .get("/list")
                .then()
                .statusCode(200)
                .extract()
                .statusLine();

        // Method 2: Using response
        String statusLine2 = given()
                .when()
                .get("/list")
                .getStatusLine()
                .trim();

        assertTrue(statusLine1.contains("HTTP/1.1 200"));
        assertEquals("HTTP/1.1 200", statusLine2);
    }

    @Description("This test will print out the Response Body")
    @Test
    public void _02_Print_ResponseBody() {
        given()
                .when()
                .get("/list")
                .then()
                .statusCode(200)
                .log()
                .body();
    }

    @Description("This test will print out Response in case of an error")
    @Test
    public void _02_Print_ResponseInCaseError() {
        given()
                .when()
                .get("/invalid")
                .then()
                .log()
                .ifError()
                .statusCode(200);
    }
}
