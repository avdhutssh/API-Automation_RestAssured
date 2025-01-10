package com.exercise._04_Json;

import static io.restassured.RestAssured.*;

import org.junit.jupiter.api.*;

public class _08_JaywayJsonPath {

    static String jsonResponse;

    @BeforeAll
    public static void getResponse() {
        baseURI = "http://localhost";
        port = 8086;
        jsonResponse = given()
                .when()
                .get("/products")
                .then()
                .extract()
                .asString();
    }

    @BeforeEach
    void printToConsoleStrt() {
        System.out.println("-----Starting the test method--------");
        System.out.println("   ");
    }

    @AfterEach
    void printToConsoleEnd() {
        System.out.println("-----Ending the test method--------");
        System.out.println("   ");
    }

    @DisplayName("Get the Root Element")
    @Test
    public void getRootElement() {
        System.out.println(jsonResponse);
    }

}
