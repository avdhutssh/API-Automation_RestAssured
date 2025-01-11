package com.exercise._04_Json;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class _09_JsonSlurperJsonPath {

    static ValidatableResponse validatableResponse;

    @BeforeAll
    public static void getResponse() {
        baseURI = "http://localhost";
        port = 8086;
        validatableResponse = given()
                .when()
                .get("/stores")
                .then();
    }

    @BeforeEach
    void printToConsoleStart(TestInfo testInfo) {
        System.out.println("-----Starting the test method: " + testInfo.getDisplayName() + "--------");
        System.out.println("   ");
    }

    @AfterEach
    void printToConsoleEnd(TestInfo testInfo) {
        System.out.println("   ");
        System.out.println("-----Ending the test method: " + testInfo.getDisplayName() + "--------");
        System.out.println("   ");
    }

    @DisplayName("Print Response")
    @Test
    public void PrintResponse() {
        validatableResponse.log().all();
    }

    @DisplayName("Print the total value from the response")
    @Test
    public void getTotal() {
        int total = validatableResponse.extract().path("total");
        System.out.println(total);
    }

    @DisplayName("Print 'storeName' from first data element")
    @Test
    public void getFirstStoreName() {
        String storeName = validatableResponse.extract().path("data[0].name");
        System.out.println(storeName);
    }

    @DisplayName("Print first 'service name' from the first data element")
    @Test
    public void getFirstServiceNameFromFirstDataElement() {
        String serviceName = validatableResponse.extract().path("data[0].services[0].name");
        System.out.println(serviceName);
    }
}
