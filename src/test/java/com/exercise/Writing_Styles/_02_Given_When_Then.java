package com.exercise.Writing_Styles;

import io.restassured.RestAssured;
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
                .body("[0].firstName", equalTo("Vernon"))
                .body("[0].courses", hasItems("Accounting", "Statistics"))
                .body("size()", greaterThan(1));
    }
}
