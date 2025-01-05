package com.exercise._02_QueryParameter;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class _04_QueryParam {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "http://localhost:8085";
        RestAssured.basePath = "/student";
    }

    @DisplayName("Getting student information using Query parameter one by one")
    @Test
    public void _01_QP_OneByOne() {
        given()
                .queryParam("programme", "Computer Science")
                .queryParam("limit", 1)
                .log()
                .all()
                .when()
                .get("/list")
                .then()
                .log()
                .all()
                .statusCode(200)
                .body("size()", equalTo(1));
    }

    @DisplayName("Getting student information using Query parameter All in one")
    @Test
    public void _02_QP_AllInOne() {
        given()
                .queryParams("programme", "Computer Science", "limit", 1)
                .log()
                .all()
                .when()
                .get("/list")
                .then()
                .log()
                .all()
                .statusCode(200)
                .body("size()", equalTo(1));
    }
}
