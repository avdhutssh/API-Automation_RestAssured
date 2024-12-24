package com.exercise.Writing_Styles;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import static org.hamcrest.Matchers.*;

public class _02_Given_When_Then {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost:8085";
        RestAssured.basePath = "/student";
    }

    @DisplayName("Basic validation of first student details")
    @Test
    void _01_validateFirstStudent() {
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

    @DisplayName("Validate complex JSON path expressions")
    @Test
    void _03_validateComplexJsonPath() {
        RestAssured.given()
                .when()
                .get("/list")
                .then()
                .statusCode(200)
                .log()
                .all()
                .body("[0].email", matchesPattern("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"));
    }

    @DisplayName("Validate error response for invalid endpoint")
    @Test
    void _04_validateErrorResponse() {
        RestAssured.given()
                .log().all()
                .when()
                .get("/invalid")
                .then()
                .log().all()
                .statusCode(400);
    }

    @DisplayName("Extract and validate response data")
    @Test
    void _05_extractAndValidateResponse() {
        List<String> firstnames = RestAssured.given()
                .when()
                .get("/list")
                .then()
                .statusCode(200)
                .log()
                .all()
                .extract()
                .path("firstName");

        assertEquals(firstnames.get(0), "Vernon");
    }

    @DisplayName("Validate using root path")
    @Test
    void _06_validateUsingRootPath() {
        RestAssured.given()
                .when()
                .get("/list")
                .then()
                .statusCode(200)
                .log()
                .all()
                .rootPath("[0]")
                .body("firstName", equalTo("Vernon"))
                .body("lastName", equalTo("Harper"))
                .body("programme", equalTo("Financial Analysis"))
                .body("courses.flatten()", hasItems("Accounting", "Statistics"));
        ;

    }
}
