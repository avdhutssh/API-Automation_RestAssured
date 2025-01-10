package com.exercise._04_Json;

import static io.restassured.RestAssured.*;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.*;

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

    static void print(String val) {
        System.out.println("-----------------------------------");
        System.out.println(val);
        System.out.println("-----------------------------------");
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
        Map<String, ?> rootElement = JsonPath.read(jsonResponse, "$");
        print(rootElement.toString());
    }

}
