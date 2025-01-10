package com.exercise._04_Json;

import static io.restassured.RestAssured.*;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInfo;

import java.util.Map;

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
    void printToConsoleStart(TestInfo testInfo) {
        System.out.println("-----Starting the test method: " + testInfo.getDisplayName() + "--------");
        // Alternative: use getTestMethod()
        System.out.println("   ");
    }

    @AfterEach
    void printToConsoleEnd(TestInfo testInfo) {
        System.out.println("   ");
        System.out.println("-----Ending the test method: " + testInfo.getDisplayName() + "--------");
        System.out.println("   ");
    }

    @DisplayName("Get the Root Element")
    @Test
    public void getRootElement() {
        Map<String, ?> rootElement = JsonPath.read(jsonResponse, "$");
        System.out.println(rootElement);
    }

    @DisplayName("Get the total value from the response")
    @Test
    public void getTotalFromResponse() {
        int total = JsonPath.read(jsonResponse, "$.total");
        System.out.println(total);
    }
}
