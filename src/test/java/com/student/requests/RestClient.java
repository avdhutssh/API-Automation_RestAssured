package com.student.requests;

import com.student.specs.SpecificationFactory;
import com.student.tests.BaseTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class RestClient extends BaseTest {

    /***
     *
     * @param requestPath
     * @return
     */
    public Response doGetRequest(String requestPath) {
        return given()
                .when()
                .get(requestPath);
    }

    /**
     * @param uri
     * @param body
     * @return
     */
    public Response doPostRequest(String uri, Object body) {

        return given()
                .contentType(ContentType.JSON)
                .spec(SpecificationFactory.logPayloadResponseInfo())
                .when()
                .body(body)
                .post(uri);
    }

    /**
     * @param res
     * @param params
     * @return
     */
    public Response doGetRequestWithQueryParam(String res,
                                               Map<String, String> params) {

        Response response = given()
                .queryParams(params)
                .when()
                .get(res);

        return response;

    }

    /**
     * @param res
     * @param headers
     * @return
     */
    public Response doGetRequestWithHeader(String res,
                                           Map<String, String> headers) {

        Response response = given()
                .headers(headers)
                .when()
                .get(res);

        return response;

    }


    /**
     * @param uri
     * @param body
     * @return
     */
    public Response doPutRequest(String uri, Object body) {

        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .body(body)
                .put(uri);

        return response;

    }

    /**
     * @param uri
     * @param body
     * @return
     */
    public Response doPatchRequest(String uri, Object body) {

        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .body(body)
                .patch(uri);

        return response;

    }

    /**
     * @param uri
     * @return
     */
    public Response doDeleteRequest(String uri) {

        Response response = given()
                .when()
                .delete(uri);

        return response;

    }
}
