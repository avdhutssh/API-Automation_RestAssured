package com.exercise.Writing_Styles;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class _01_Validatable_Response {

    @DisplayName("Getting all the student from the database")
    @Test
    void getAllStudents(){
        RequestSpecification requestSpec = RestAssured.given();
        Response response = requestSpec.get("http://localhost:8085/student/list");
//        response.prettyPrint();

        System.out.println("-----------------------------------------");

        ValidatableResponse validatableResp = response.then();

        validatableResp.log().all();
        validatableResp.statusCode(200);
        validatableResp.time(lessThan(2000L));
        validatableResp.contentType(ContentType.JSON);
        validatableResp.header("Transfer-Encoding","chunked");
        validatableResp.body("[0].firstName",equalTo("Vernon"));
        validatableResp.body("[1].firstName", equalTo("Murphy"));
        validatableResp.body("firstName",hasItems("Vernon","Murphy","Reece","Orson"));
        validatableResp.body("size()", equalTo(100));
        validatableResp.body("find{it.firstName == 'Vernon'}.lastName", equalTo("Harper"));
        validatableResp.body("findAll{it.programme =='Financial Analysis'}.size()", greaterThan(5));
        validatableResp.body("findAll{it.programme =='Financial Analysis'}.size()",equalTo(10));




    }
}
