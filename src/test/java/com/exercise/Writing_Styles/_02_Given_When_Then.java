package com.exercise.Writing_Styles;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class _02_Given_When_Then {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost:8085";
        RestAssured.basePath = "/student";
    }

    @DisplayName("Basic validation of first student details")
    @Test
    void _02_validateFirstStudent() {
        RestAssured.given()
                .log()
                .all()
                .when()
                .get("/list")
                .then()
                .statusCode(200)
                .log()
                .all()
                .headers("Content-Type", "application/json;charset=UTF-8",
                        "Transfer-Encoding", "chunked")
                .time(lessThan(3000L))
                .contentType(ContentType.JSON)
                .body("[0].firstName", equalTo("Vernon"))
                .body("findAll{it.courses.contains('Accounting')}.firstName", hasItems("Vernon"))
                .body("[0].courses", hasItems("Accounting", "Statistics"))
                .body("size()", greaterThan(1));
    }

    @DisplayName("Validate response with Request headers and parameters")
    @Test
    void _02_validateWithRequestHeadersAndParams() {
        RestAssured.given()
                .log()
                .all()
                .header("Content-Type", "application/json")
                .param("programme", "Financial Analysis")
                .when()
                .get("/list")
                .then()
                .log().all()
                .statusCode(200)
                .body("findAll{it.programme=='Financial Analysis'}.size()", equalTo(10));
    }

}
