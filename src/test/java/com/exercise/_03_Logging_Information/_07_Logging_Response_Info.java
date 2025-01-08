package com.exercise._03_Logging_Information;

import com.github.javafaker.Faker;
import com.student.pojo.StudentClass;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

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

}
