package com.student.tests;

import com.github.javafaker.Faker;
import com.student.requests.RequestFactory;
import com.student.specs.SpecificationFactory;
import com.student.util.AssertionUtils;
import com.student.util.TestUtils;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CRUD_Test extends BaseTest {

    private RequestFactory request;
    private String lastID;
    private Faker randomData;

    @BeforeEach
    public void setUp() {
        request = new RequestFactory();
        randomData = new Faker();
    }

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
    @Tag("Regression")
    @Tag("Smoke")
    @Test
    public void _02_createNewStudent() {
        String firstName = randomData.name().firstName();
        String lastName = randomData.name().lastName();
        String email = "testAvPost_" + randomData.internet().emailAddress();
        String programme = "ComputerScience";
        List<String> courses = createCourseList();

        ValidatableResponse response = request.createNewStudent("", firstName, lastName, email, programme, courses)
                .then()
                .spec(SpecificationFactory.getGenericResponseSpec());

        AssertionUtils.verifyStatusCode(response, 201);
        AssertionUtils.verifyResponsePayload(response, "msg", "Student added");

        fetchLastStudentId();
        logNewStudentID(lastID);
    }

    @Tag("Regression")
    @Story("This is a CRUD testing story")
    @DisplayName("Test to update an existing student")
    @Feature("Test to update an existing student")
    @Test
    public void _03_updateStudentInfo() {
        fetchLastStudentId();
        String firstName = randomData.name().firstName();
        String lastName = randomData.name().lastName();
        String email = "testAvPut_" + randomData.internet().emailAddress();
        String programme = "ComputerScience";
        List<String> courses = createCourseList();

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
        fetchLastStudentId();
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
    @Tag("Regression")
    @Tag("Smoke")
    @Test
    public void _05_deleteStudentFromDB() {
        fetchLastStudentId();
        logDeletingStudent(lastID);
        ValidatableResponse response = request.deleteStudent(lastID)
                .then();

        AssertionUtils.verifyStatusCode(response, 204);
    }

    private void fetchLastStudentId() {
        ValidatableResponse response = request.getAllStudents()
                .then()
                .spec(SpecificationFactory.getGenericResponseSpec());
        AssertionUtils.verifyStatusCode(response, 200);
        this.lastID = TestUtils.getJsonPayloadValue(response, "[-1].id");
    }

    private List<String> createCourseList() {
        List<String> courses = new ArrayList<>();
        courses.add("Java");
        courses.add("Rest Assured");
        return courses;
    }

    private void logNewStudentID(String lastID) {
        System.out.println("Newly created student ID is: " + lastID);
    }

    private void logDeletingStudent(String lastID) {
        System.out.println("Deleting student with ID: " + lastID);
    }
}
