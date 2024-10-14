package com.student.tests;

import com.github.javafaker.Faker;
import com.student.pojo.StudentClass;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class e2eStandalone extends BaseTest{

	@Test
	public void getAllStudents() {

		// 1. Get Response As String
        String response1 = given().when().get("http://localhost:8085/student/list").then().extract().response().asString();
		System.out.println(response1);

		// Assert response payload using JsonPath
		JsonPath jp1 = new JsonPath(response1);
		String firstName1 = jp1.get("[0].firstName");
		System.out.println(firstName1);
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		// 2. Get Response and print
		Response response2 = given().when().get("http://localhost:8085/student/list");
		response2.prettyPrint();
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		// 3. Get ValidatableResponse
		ValidatableResponse response3 = given().when().get("http://localhost:8085/student/list").then();
		response3.statusCode(200);
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		// 4. logAll info for request and response
		ValidatableResponse response4 = given().log().all().when().get("http://localhost:8085/student/list").then().log().all();
		response4.statusCode(200);
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		// 5. Use Expect and verify status code
		Response response5 = given().expect().statusCode(200).when().get("http://localhost:8085/student/list");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
	}


}
	