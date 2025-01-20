package com.exercise._05_Xml;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class XMLPathExample {

    @BeforeAll
    public static void init() {
        RestAssured.baseURI = "https://api.weather.gov";
    }

    @Description("Verify status code and content type")
    @Test
    public void _01_VerifyContentType() {
        given()
                .header("Accept", "application/xml")
                .when()
                .get("/points/40.7128,-74.0060")
                .then()
                .statusCode(200)
                .contentType(containsString("application/xml"));
    }

}
