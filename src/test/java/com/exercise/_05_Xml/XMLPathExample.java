package com.exercise._05_Xml;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class XMLPathExample {

    @BeforeAll
    public static void init() {
//        RestAssured.baseURI = "https://api.weather.gov";
        RestAssured.baseURI = "http://webservices.oorsprong.org/websamples.countryinfo";
    }

    @Description("Verify status code and content type")
    @Test
    public void _01_VerifyContentType() {
        given()
                .contentType("text/xml")
                .body("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://www.oorsprong.org/websamples.countryinfo\">\n" +
                        "   <soapenv:Header/>\n" +
                        "   <soapenv:Body>\n" +
                        "      <web:CapitalCity>\n" +
                        "         <web:sCountryISOCode>US</web:sCountryISOCode>\n" +
                        "      </web:CapitalCity>\n" +
                        "   </soapenv:Body>\n" +
                        "</soapenv:Envelope>")
                .when()
                .post("/CountryInfoService.wso")
                .then()
                .statusCode(200)
                .log()
                .all()
                .contentType(containsString("text/xml"));
    }

//    @Description("Verify status code and content type")
//    @Test
//    public void _01_VerifyContentType() {
//        given()
//                .header("Accept", "application/xml")
//                .when()
//                .get("/points/40.7128,-74.0060")
//                .then()
//                .statusCode(200)
//                .log()
//                .all();
////                .contentType(containsString("application/xml"));
//
//    }

}
