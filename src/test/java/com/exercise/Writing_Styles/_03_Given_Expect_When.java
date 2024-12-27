package com.exercise.Writing_Styles;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

    @DisplayName("Validate multiple fields using expect")
    @Test
    void _02_validateMultipleFields() {
        RestAssured.given()
                .log().all()
                .expect()
                .statusCode(200)
                .body("[0].firstName", equalTo("Vernon"))
                .body("[0].lastName", equalTo("Harper"))
                .body("[0].programme", equalTo("Financial Analysis"))
                .body("[0].courses", hasItems("Accounting", "Statistics"))
                .when()
                .get("/list");
    }

    @DisplayName("Complex JSON path validations")
    @Test
    void _03_complexJsonPathValidations() {
        RestAssured.given()
                .expect()
                .statusCode(200)
                .body("findAll{it.programme=='Financial Analysis'}.size()", equalTo(10))
                .body("findAll{it.courses.contains('Accounting')}.firstName", hasItems("Vernon"))
                .body("findAll{it.email != null}.size()", equalTo(100))
                .body("email.size()", equalTo(100))
                .body("email", hasSize(equalTo(100)))
                .body("[0].email", matchesPattern("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"))
                .when()
                .get("/list");
    }

    @DisplayName("Validate array properties")
    @Test
    void _04_validateArrayProperties() {
        RestAssured.given()
                .expect()
                .statusCode(200)
                .body("size()", greaterThan(1))
                .body("firstName", hasSize(greaterThan(1)))
                .body("courses.flatten()", hasItems("Accounting", "Statistics"))
                .body("courses.flatten()", hasItems("Accounting"))
                .body("courses.flatten().unique()", hasItems("Accounting", "Statistics"))
                .body("courses.flatten().size()", greaterThan(10))
                .body("courses.flatten().unique().size()", greaterThan(2))
                .when()
                .get("/list");
    }
}
