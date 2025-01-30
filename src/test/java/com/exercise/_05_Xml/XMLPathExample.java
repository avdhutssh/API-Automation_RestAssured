package com.exercise._05_Xml;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class XMLPathExample {
    private static String pet_ID;

    @BeforeAll
    public static void init() {
//        RestAssured.baseURI = "https://api.weather.gov";
//        RestAssured.baseURI = "http://webservices.oorsprong.org/websamples.countryinfo";
        RestAssured.baseURI = "https://petstore.swagger.io";
        RestAssured.basePath = "/v2";
        // Create pet and store ID in BeforeClass
        String xmlBody = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<Pet>" +
                "<id>0</id>" +
                "<Category>" +
                "<id>0</id>" +
                "<name>string</name>" +
                "</Category>" +
                "<name>doggie</name>" +
                "<photoUrls>" +
                "<photoUrl>string</photoUrl>" +
                "</photoUrls>" +
                "<tags>" +
                "<Tag>" +
                "<id>0</id>" +
                "<name>string</name>" +
                "</Tag>" +
                "</tags>" +
                "<status>available</status>" +
                "</Pet>";

        pet_ID = given()
                .contentType("application/xml")
                .accept("application/xml")
                .body(xmlBody)
                .when()
                .post("/pet")
                .then()
                .statusCode(200)
                .log()
                .all()
                .extract()
                .path("Pet.id")
                .toString();

        System.out.println("Created pet with ID: " + pet_ID);
    }

    @Description("Verify status code and content type")
    @Test
    public void _01_VerifyContentType1() {
        given()
                .accept("application/xml")
                .pathParam("petId", pet_ID)
                .when()
                .get("/pet/{petId}")
                .then()
                .statusCode(200)
                .log()
                .all()
                .contentType("application/xml");
    }

    @Description("Extract and verify pet ID")
    @Test
    public void _02_VerifyPetID() {
        String id = given()
                .accept("application/xml")
                .pathParam("petId", pet_ID)
                .when()
                .get("/pet/{petId}")
                .then()
                .statusCode(200)
                .extract()
                .path("Pet.id")
                .toString();

        System.out.println("Pet ID is : " + id);
        assertEquals(pet_ID, id, "ID does not match");
    }

//    @Description("Verify status code and content type")
//    @Test
//    public void _01_VerifyContentType() {
//        given()
//                .contentType("text/xml")
//                .body("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://www.oorsprong.org/websamples.countryinfo\">\n" +
//                        "   <soapenv:Header/>\n" +
//                        "   <soapenv:Body>\n" +
//                        "      <web:CapitalCity>\n" +
//                        "         <web:sCountryISOCode>US</web:sCountryISOCode>\n" +
//                        "      </web:CapitalCity>\n" +
//                        "   </soapenv:Body>\n" +
//                        "</soapenv:Envelope>")
//                .when()
//                .post("/CountryInfoService.wso")
//                .then()
//                .statusCode(200)
//                .log()
//                .all()
//                .contentType(containsString("text/xml"));
//    }
//
//    @Description("Verify status code and content type")
//    @Test
//    public void _01_VerifyContentType1() {
//        given()
//                .header("Accept", "application/xml")
//                .when()
//                .get("/points/40.7128,-74.0060")
//                .then()
//                .statusCode(200)
//                .log()
//                .all();
////                .contentType(containsString("application/xml"));
//    }

}
