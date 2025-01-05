package com.exercise._03_Logging_Information;

import com.github.javafaker.Faker;
import com.student.pojo.StudentClass;
import io.qameta.allure.Description;

import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class _06_Logging_Request_Info {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8085;
        RestAssured.basePath = "/student";
    }

    @Description("This test will print out all the request headers")
    @Test
    public void _01_Print_AllRequestHeaders() {
        given()
                .log()
                .headers()
                .when()
                .get("/list")
                .then()
                .statusCode(200);
    }

    @Description("This test will print out all the request parameters")
    @Test
    public void _02_Print_AllRequestParameters() {
        given()
                .param("programme", "Computer Science")
                .param("limit", 1)
                .log()
//                .parameters()
                .params()
                .when()
                .get("/list")
                .then()
                .statusCode(200);
    }

    @Description("This test will print out the Request body")
    @Test
    public void _03_Print_RequestBody() {
        StudentClass student = new StudentClass();
        Faker randomData = new Faker();

        List<String> courses = new ArrayList<>();
        courses.add("Java");
        courses.add("Rest Assured");

        student.setFirstName(randomData.name().firstName());
        student.setLastName(randomData.name().lastName());
        student.setEmail(randomData.internet().emailAddress());
        student.setProgramme("Computer Science");
        student.setCourses(courses);

        String response = given()
                .log()
                .body()
                .when()
                .contentType(ContentType.JSON)
                .body(student)
                .post()
                .then()
                .statusCode(201)
                .extract()
                .asString();

        System.out.println(response);
    }

}
