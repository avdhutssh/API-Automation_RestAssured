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

	@Test
	public void getStudentsInfoUsingQueryParameter_Way1(){
		String response = given()
				.queryParam("programme", "Computer Science")
				.queryParam("limit", 1)
				.when()
				.get("http://localhost:8085/student/list")
				.then()
				.statusCode(200)
				.statusLine("HTTP/1.1 200 ")
				.body("[0].firstName", equalTo("Reece"))  //Assertion
				.extract()
				.asString();

		System.out.println(response);

		JsonPath jp = new JsonPath(response);
		String name = jp.get("[0].firstName");
		System.out.println(name);

	}

	@Test
	public void getStudentsInfoUsingQueryParameter_Way2_AllInOne(){
		String response = given()
				.queryParams("programme", "Computer Science", "limit", 1)
				.expect()
				.statusCode(200)
				.when()
				.get("http://localhost:8085/student/list")
				.then()
				.extract()
				.asString();

		System.out.println(response);

	}

	@Test
	public void getStudentsInfoUsingQueryParameter_Way3_Using_Map(){

		Map<String,Object> hm = new HashMap<>();
		hm.put("programme", "Computer Science");
		hm.put("limit",1);
		Response response = given()
				.queryParams(hm)
				.expect()
				.statusCode(200)
				.when()
				.get("http://localhost:8085/student/list");

		response.prettyPrint();

	}

	@Test
	public void getSpecificStudentsInfoUsingPathParameter(){

		Response response = given()
				.pathParam("id", 3)
				.expect()
				.statusCode(200)
				.when()
				.get("http://localhost:8085/student/{id}");

		response.prettyPrint();

	}

	@Test
	public void createNewStudentUsingStaticPayload(){

		String requestPayload = "{\n" +
				"    \"firstName\": \"hkkjj\",\n" +
				"    \"lastName\": \"jjljl\",\n" +
				"    \"email\": \"test1234@gmail.com\",\n" +
				"    \"programme\": \"Computer Science\",\n" +
				"    \"courses\": [\n" +
				"        \"Java\",\n" +
				"        \"RestAssured\"\n" +
				"    ]\n" +
				"}";

		String response = given()
				.log().all()
				.when()
				.contentType(ContentType.JSON)
				.body(requestPayload)
				.post()
				.then()
				.statusCode(201)
				.extract()
				.asString();

		System.out.println(response);
	}

	@Test
	public void createNewStudentUsingPOJO(){

		StudentClass student = new StudentClass();
		Faker randomData = new Faker();

		List<String>  courses = new ArrayList<>();
		courses.add("Java");
		courses.add("Rest Assured");

		student.setFirstName(randomData.name().firstName());
		student.setLastName(randomData.name().lastName());
		student.setEmail(randomData.internet().emailAddress());
		student.setProgramme("Computer Science");
		student.setCourses(courses);

		String response = given()
				.log().all()
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


	@Test
	public void updateStudentUsingPOJO(){

		StudentClass student = new StudentClass();
		Faker randomData = new Faker();

		List<String>  courses = new ArrayList<>();
		courses.add("Java");
		courses.add("Rest Assured");
		student.setFirstName("Oscar");
		student.setLastName("Barclay");
		student.setEmail("testAvPut"+ randomData.internet().emailAddress());
		student.setProgramme("Computer Science");
		student.setCourses(courses);

		String response = given()
				.log().all()
				.when()
				.contentType(ContentType.JSON)
				.body(student)
				.put("/101")
				.then()
				.statusCode(200)
				.extract()
				.asString();

		System.out.println(response);
	}

	@Test
	public void updateStudentEmailUsingPOJO(){

		StudentClass student = new StudentClass();
		Faker randomData = new Faker();

		String dummyEmail = "testAvPatch"+ randomData.internet().emailAddress();
		student.setEmail(dummyEmail);

		given()
				.log().all()
				.when()
				.contentType(ContentType.JSON)
				.body(student)
				.patch("/101")
				.then()
				.statusCode(200)
				.log().body()
				.extract()
				.path("email", String.valueOf(equalTo(dummyEmail))); //Assertion

	}

	@Test
	public void deleteStudent(){
		String response1 = given()
				.when()
				.get("/list")
				.then()
				.extract()
				.response()
				.asString();
		System.out.println(response1);
		JsonPath jp1 = new JsonPath(response1);
		int lastId = jp1.get("[-1].id");
		System.out.println("last Id is: "+lastId);

		String response = given()
				.log().all()
				.when()
				.contentType(ContentType.JSON)
				.delete("/"+lastId)
				.then()
				.statusCode(204)
				.extract()
				.asString();

		System.out.println(response);
	}
}
	