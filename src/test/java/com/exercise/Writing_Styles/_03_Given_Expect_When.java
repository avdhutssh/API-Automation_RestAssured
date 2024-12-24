package com.exercise.Writing_Styles;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class _03_Given_Expect_When {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "http://localhost:8085";
        RestAssured.basePath = "/student";
    }

    @DisplayName("Basic validation of first student details")
    @Test
    void _01_validateFirstStudent() {
        RestAssured.given()
                .log()
                .all()
                .expect()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .headers("Content-Type", "application/json;charset=UTF-8",
                        "Transfer-Encoding", "chunked")
                .time(lessThan(3000L))
                .body("[0].firstName", equalTo("Vernon"))
                .body("findAll{it.courses.contains('Accounting')}.firstName", hasItems("Vernon"))
                .body("[0].courses", hasItems("Accounting", "Statistics"))
                .body("size()", greaterThan(99))
                .when()
                .get("/list");
    }

}
