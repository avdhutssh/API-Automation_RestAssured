package com.exercise._04_Json;

import static io.restassured.RestAssured.*;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInfo;

import java.util.HashMap;
import java.util.List;
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

    @DisplayName("Get all the data elements")
    @Test
    public void getAllDataElements() {
        List<HashMap<String, Object>> dataElements = JsonPath.read(jsonResponse, "$.data");
        dataElements.stream().forEach(System.out::println);
    }

    @DisplayName("Get first Data Element")
    @Test
    public void getFirstDataElement() {
        Map<String, ?> firstDataElement = JsonPath.read(jsonResponse, "$.data[0]");
        System.out.println(firstDataElement);
    }

    @DisplayName("Get last Data Element")
    @Test
    public void getLastDataElement() {
        Map<String, ?> lastDataElement = JsonPath.read(jsonResponse, "$.data[-1]");
        System.out.println(lastDataElement);
    }

    @DisplayName("Get All the Ids")
    @Test
    public void getAllIds() {
        List<String> allIds1 = JsonPath.read(jsonResponse, "$..id");
        System.out.println(allIds1);
    }
}
