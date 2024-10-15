package com.student.requests;

import com.student.pojo.StudentClass;
import com.student.tests.BaseTest;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.List;

public class RequestFactory extends BaseTest {

    RestClient restClient = new RestClient();

    @Step("Getting all students information from the DB")
    public Response getAllStudents() {

        return restClient.doGetRequest("/list");
    }

    @Step("Creating a new student : {0} , {1} , {2} ,{3} , {4}")
    public Response createNewStudent(String url, String firstName, String lastName, String email,
                                     String programme, List<String> courses) {

        StudentClass studentReqPayload = new StudentClass();
        studentReqPayload.setFirstName(firstName);
        studentReqPayload.setLastName(lastName);
        studentReqPayload.setEmail(email);
        studentReqPayload.setProgramme(programme);
        studentReqPayload.setCourses(courses);

        return restClient.doPostRequest(url, studentReqPayload);
    }


    @Step("Updating student information with studentId: {0}, firstName: {1}, lastName: {2}, email: {3}, programme: {4}, courses: {5}")
    public Response updateStudent(String studentid, String firstName,
                                  String lastName, String email, String programme,
                                  List<String> courses) {

        StudentClass student = new StudentClass();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setEmail(email);
        student.setProgramme(programme);
        student.setCourses(courses);

        return restClient.doPutRequest("/" + studentid, student);

    }

    @Step("Updating student information with Student_Id: {0}, email: {1}")
    public Response updateStudentEmailAddress(String studentId, String email) {

        StudentClass student = new StudentClass();
        student.setEmail(email);
        return restClient.doPatchRequest("/" + studentId, student);

    }

    @Step("Deleting student info with Id: {0}")
    public Response deleteStudent(String studentId) {

        return restClient.doDeleteRequest("/" + studentId);

    }

    public Response getStudentById(String studentId) {

        return restClient.doGetRequest("/" + studentId);


    }
}
