package com.paypal.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.Matchers;

import java.util.concurrent.TimeUnit;

public class ResponseSpecificationBuilder {
    private static final Logger log = LogManager.getLogger(ResponseSpecificationBuilder.class);

    public static ResponseSpecification getGenericResponseSpec() {
        log.info("Building generic response specification");

        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectResponseTime(Matchers.lessThan(10000L), TimeUnit.MILLISECONDS)
                .build();
    }

    public static ResponseSpecification getSuccessResponseSpec() {
        log.info("Building success response specification");

        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .expectResponseTime(Matchers.lessThan(10000L), TimeUnit.MILLISECONDS)
                .build();
    }

    public static ResponseSpecification getCreatedResponseSpec() {
        log.info("Building created response specification");

        return new ResponseSpecBuilder()
                .expectStatusCode(201)
                .expectContentType(ContentType.JSON)
                .expectResponseTime(Matchers.lessThan(10000L), TimeUnit.MILLISECONDS)
                .build();
    }

    public static ResponseSpecification getNoContentResponseSpec() {
        log.info("Building no content response specification");

        return new ResponseSpecBuilder()
                .expectStatusCode(204)
                .expectResponseTime(Matchers.lessThan(10000L), TimeUnit.MILLISECONDS)
                .build();
    }

    public static ResponseSpecification getOrderCreationSuccessSpec() {
        log.info("Building order creation success response specification");

        return new ResponseSpecBuilder()
                .expectStatusCode(201)
                .expectContentType(ContentType.JSON)
                .expectBody("id", Matchers.notNullValue())
                .expectBody("status", Matchers.equalTo("CREATED"))
                .expectResponseTime(Matchers.lessThan(10000L), TimeUnit.MILLISECONDS)
                .build();
    }

    public static ResponseSpecification getResponseSpecWithStatus(int statusCode) {
        log.info("Building response specification with status code: {}", statusCode);

        return new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .expectContentType(ContentType.JSON)
                .expectResponseTime(Matchers.lessThan(10000L), TimeUnit.MILLISECONDS)
                .build();
    }
}