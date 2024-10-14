package com.student.specs;

import com.student.tests.BaseTest;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.lessThan;

public class SpecificationFactory extends BaseTest {

//    public static ResponseSpecification verifyStatusCodeAndGiveStringResponse(int StatusCode) {
//
//        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
//        responseSpecBuilder.expectHeader("Content-Type", "application/json;charset=UTF-8");
//        responseSpecBuilder.expectHeader("Transfer-Encoding", "chunked");
//        responseSpecBuilder.expectStatusCode(StatusCode);
//
//        ResponseSpecification responseSpecification = responseSpecBuilder.build();
//        return responseSpecification;
//    }


    public static ResponseSpecification getGenericResponseSpec() {

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectHeader("Content-Type", "application/json;charset=UTF-8");
        responseSpecBuilder.expectHeader("Transfer-Encoding", "chunked");
        responseSpecBuilder.expectResponseTime(lessThan(5L), TimeUnit.SECONDS);

        ResponseSpecification responseSpecification = responseSpecBuilder.build();
        return responseSpecification;
    }

    public static synchronized RequestSpecification logPayloadResponseInfo() {
        RequestSpecBuilder logBuilder = new RequestSpecBuilder();


        if (prop.getProperty("log").equals("ENABLE")) {

            logBuilder.addFilter(new AllureRestAssured());

        }

        RequestSpecification logSpecification = logBuilder.build();
        return logSpecification;

    }
}
