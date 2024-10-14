package com.student.tests;

import com.github.javafaker.Faker;
import com.student.requests.RequestFactory;
import com.student.specs.SpecificationFactory;
import com.student.util.AssertionUtils;
import com.student.util.TestUtils;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CRUD_Test extends BaseTest {

    RequestFactory request = new RequestFactory();
    String lastID;

    @Tag("Smoke")
    @Story("This is a CRUD testing story")
    @DisplayName("This a test to get all students from the database")
    @Feature("This a test to get all students from the database")
    @Test
    public void _01_getAllStudentsInfo() {

        ValidatableResponse response = request.getAllStudents()
                .then()
                .spec(SpecificationFactory.getGenericResponseSpec());

        AssertionUtils.verifyStatusCode(response, 200);
        AssertionUtils.verifyResponsePayload(response, "[0].firstName", "Vernon");

    }

    @Story("This is a CRUD testing story")
    @DisplayName("Test to create & verify a new student")
    @Feature("Test to create & verify a new student")
    @Tag("Regression,Smoke")
    @Test
    public void _02_createNewStudent() {
        Faker randomData = new Faker();

        String firstName = randomData.name().firstName();
        String lastName = randomData.name().lastName();
        String email = "testAvPost_" + randomData.internet().emailAddress();

        String programme = "ComputerScience";

        List<String> courses = new ArrayList<String>();
        courses.add("Java");
        courses.add("Rest Assured");

        ValidatableResponse response = request.createNewStudent("", firstName, lastName, email, programme, courses)
                .then()
                .spec(SpecificationFactory.getGenericResponseSpec());

        AssertionUtils.verifyStatusCode(response, 201);
        AssertionUtils.verifyResponsePayload(response, "msg", "Student added");

        this.getLastStudentID();
        System.out.println("Newly created student ID is: " + lastID);
    }

    @Tag("Regression")
    @Story("This is a CRUD testing story")
    @DisplayName("Test to update an existing student")
    @Feature("Test to update an existing student")
    @Test
    public void _03_updateStudentInfo() {

        this.getLastStudentID();
        Faker randomData = new Faker();
        String firstName = randomData.name().firstName();
        String lastName = randomData.name().lastName();
        String email = "testAvPut_" + randomData.internet().emailAddress();
        String programme = "ComputerScience";
        List<String> courses = new ArrayList<>();
        courses.add("Java");
        courses.add("Rest Assured");

        ValidatableResponse response = request.updateStudent(lastID, firstName, lastName, email, programme, courses)
                .then()
                .spec(SpecificationFactory.getGenericResponseSpec());

        AssertionUtils.verifyStatusCode(response, 200);
        AssertionUtils.verifyResponsePayload(response, "msg", "Student Updated");

    }

    @Tag("Regression")
    @Story("This is a CRUD testing story")
    @DisplayName("Test to update student email")
    @Feature("Test to update student email")
    @Test
    public void _04_updateStudentEmail() {
        this.getLastStudentID();
        Faker randomData = new Faker();
        String email = "testAvPatch_" + randomData.internet().emailAddress();

        ValidatableResponse response = request.updateStudentEmailAddress(lastID, email)
                .then()
                .spec(SpecificationFactory.getGenericResponseSpec());

        AssertionUtils.verifyStatusCode(response, 200);
        AssertionUtils.verifyResponsePayload(response, "msg", "Updated");

    }

    @Story("This is a CRUD testing story")
    @DisplayName("Test to delete student")
    @Feature("Test to delete student")
    @Tag("Regression,Smoke")
    @Test
    public void _05_deleteStudentFromDB() {

        this.getLastStudentID();
        System.out.println("last Id is: " + lastID);
        ValidatableResponse response = request.deleteStudent(lastID)
                .then();

        AssertionUtils.verifyStatusCode(response, 204);

    }

    public String getLastStudentID() {
        ValidatableResponse response = request.getAllStudents()
                .then()
                .spec(SpecificationFactory.getGenericResponseSpec());
        AssertionUtils.verifyStatusCode(response, 200);
        String lastId = TestUtils.getJsonPayloadValue(response, "[-1].id");
        this.lastID = lastId;
        return lastID;

    }
}
