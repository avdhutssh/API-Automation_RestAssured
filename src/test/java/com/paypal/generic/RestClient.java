package com.paypal.generic;

import com.paypal.specs.RequestSpecificationBuilder;
import com.paypal.specs.ResponseSpecificationBuilder;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class RestClient {
    private static final Logger log = LogManager.getLogger(RestClient.class);

    private static ResponseSpecification respSpecGeneric = ResponseSpecificationBuilder.getGenericResponseSpec();
    private static ResponseSpecification respSpecNoContentGeneric = ResponseSpecificationBuilder.getGenericResponseNoContentSpec();

    private static RequestSpecification reqSpecAuth;

    @Step("Execute GET request on {endpoint} with path parameters and authorization token")
    public Response doGetRequest(String endpoint, Map<String, Object> pathParams, String accessToken) {
        log.info("Executing GET request on endpoint: {} with path params: {} and token", endpoint, pathParams);
        reqSpecAuth = RequestSpecificationBuilder.getAuthenticatedRequestSpec(accessToken);

        return RestAssured.given()
                .spec(reqSpecAuth)
                .pathParams(pathParams)
                .when()
                .get(endpoint)
                .then()
                .spec(respSpecGeneric)
                .extract()
                .response();
    }

    @Step("Execute POST request on {endpoint} with authorization token")
    public Response doPostRequest(String endpoint, Object requestBody, String accessToken) {
        log.info("Executing POST request on endpoint: {} with token", endpoint);
        reqSpecAuth = RequestSpecificationBuilder.getAuthenticatedRequestSpec(accessToken);

        return RestAssured.given()
                .spec(reqSpecAuth)
                .body(requestBody)
                .when()
                .post(endpoint)
                .then()
                .spec(respSpecGeneric)
                .extract()
                .response();
    }

    @Step("Execute POST request on {endpoint} with path parameters and authorization token")
    public Response doPostRequest(String endpoint, Object requestBody, Map<String, Object> pathParams, String accessToken) {
        log.info("Executing POST request on endpoint: {} with path params: {} and token", endpoint, pathParams);
        reqSpecAuth = RequestSpecificationBuilder.getAuthenticatedRequestSpec(accessToken);

        return RestAssured.given()
                .spec(reqSpecAuth)
                .body(requestBody)
                .pathParams(pathParams)
                .when()
                .post(endpoint)
                .then()
                .spec(respSpecGeneric)
                .extract()
                .response();
    }

    @Step("Execute PATCH request on {endpoint} with path parameters and authorization token")
    public Response doPatchRequest(String endpoint, Object requestBody, Map<String, Object> pathParams, String accessToken) {
        log.info("Executing PATCH request on endpoint: {} with path params: {} and token", endpoint, pathParams);
        reqSpecAuth = RequestSpecificationBuilder.getAuthenticatedRequestSpec(accessToken);

        return RestAssured.given()
                .spec(reqSpecAuth)
                .body(requestBody)
                .pathParams(pathParams)
                .when()
                .patch(endpoint)
                .then()
                .spec(respSpecNoContentGeneric)
                .extract()
                .response();
    }

    @Step("Execute PUT request on {endpoint} with path parameters and authorization token")
    public Response doPutRequest(String endpoint, Object requestBody, Map<String, Object> pathParams, String accessToken) {
        log.info("Executing PUT request on endpoint: {} with path params: {} and token", endpoint, pathParams);
        reqSpecAuth = RequestSpecificationBuilder.getAuthenticatedRequestSpec(accessToken);

        return RestAssured.given()
                .spec(reqSpecAuth)
                .body(requestBody)
                .pathParams(pathParams)
                .when()
                .put(endpoint)
                .then()
                .spec(respSpecGeneric)
                .extract()
                .response();
    }

    @Step("Execute DELETE request on {endpoint} with path parameters and authorization token")
    public Response doDeleteRequest(String endpoint, Map<String, Object> pathParams, String accessToken) {
        log.info("Executing DELETE request on endpoint: {} with path params: {} and token", endpoint, pathParams);
        reqSpecAuth = RequestSpecificationBuilder.getAuthenticatedRequestSpec(accessToken);

        return RestAssured.given()
                .spec(reqSpecAuth)
                .pathParams(pathParams)
                .when()
                .delete(endpoint)
                .then()
                .spec(respSpecGeneric)
                .extract()
                .response();
    }
}