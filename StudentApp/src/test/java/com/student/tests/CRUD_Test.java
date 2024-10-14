package com.student.tests;

import com.student.requests.RequestFactory;
import com.student.specs.SpecificationFactory;
import com.student.util.AssertionUtils;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CRUD_Test extends BaseTest {

    RequestFactory request = new RequestFactory();

    
    @Story("This is a CRUD testing story")
    @DisplayName("This a test to get all students from the database")
    @Feature("This a test to get all students from the database")
    @Test
    public void getAllStudentsInfo() {

        ValidatableResponse response = request.getAllStudents()
                .then()
                .spec(SpecificationFactory.getGenericResponseSpec());

        AssertionUtils.verifyStatusCode(response, 200);
        AssertionUtils.verifyResponsePayload(response, "[0].firstName", "Vernon");

    }

    @Test
    public void createNewStudent() {

    }

    @Test
    public void updateStudentInfo() {

    }

    @Test
    public void updateStudentEmail() {

    }

    @Test
    public void deleteStudentFromDB() {

    }
}
