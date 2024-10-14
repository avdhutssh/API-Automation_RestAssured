package com.student.tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class CRUD_Test extends BaseTest{

    @Test
    public void getAllStudentsInfo(){

       String response =  given()
                          .when()
                          .get("/list")
                          .then()
                          .statusCode(200)
                          .extract()
                          .asString();
        System.out.println(response);
    }

    @Test
    public void createNewStudent(){

    }

    @Test
    public void updateStudentInfo(){

    }

    @Test
    public void updateStudentEmail(){

    }

    @Test
    public void deleteStudentFromDB(){

    }
}
