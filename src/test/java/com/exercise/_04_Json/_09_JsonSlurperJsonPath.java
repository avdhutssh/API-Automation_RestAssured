package com.exercise._04_Json;

import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.*;

import java.util.LinkedHashMap;
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

    @DisplayName("Get all the info of store with zip 55901")
    @Test
    public void findStoreWithZip() {
        String storeName = validatableResponse.extract().path("data.find{it.zip=='55901'}.name");
        System.out.println(storeName);
    }

    @DisplayName("Get 'address' of store with zip 55901")
    @Test
    public void findAddressOfStoreWithZip() {
        // $.data[?(@.zip=='55901')].address
        String address = validatableResponse.extract().path("data.find{it.zip == '55901'}.address");
        System.out.println(address);
    }

    @DisplayName("Get all information of store with max & min id")
    @Test
    public void getInfoOfStoreWithMinMaxId() {
        LinkedHashMap<String, Object> minID = validatableResponse.extract().path("data.min{it.id}");
        System.out.println(minID);

        LinkedHashMap<String, Object> maxID = validatableResponse.extract().path("data.max{it.id}");
        System.out.println(maxID);
    }
}
