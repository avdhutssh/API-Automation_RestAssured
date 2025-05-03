package com.paypal.specs;


import com.paypal.utils.AllureRestAssuredFilter;
import com.paypal.utils.PropertyReader;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RequestSpecificationBuilder {
    private static final Logger log = LogManager.getLogger(RequestSpecificationBuilder.class);
    private static final PropertyReader propertyReader = PropertyReader.getInstance();

    public static RequestSpecification getDefaultRequestSpec() {
        log.info("Building default request specification");

        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addFilter(AllureRestAssuredFilter.getInstance())
                .log(LogDetail.ALL)
                .build();
    }

    public static RequestSpecification getAuthenticatedRequestSpec(String accessToken) {
        log.info("Building authenticated request specification");

        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "Bearer " + accessToken)
                .addFilter(new AllureRestAssured())
                .log(LogDetail.ALL)
                .build();
    }

    public static RequestSpecification getFormUrlEncodedRequestSpec() {
        log.info("Building form URL encoded request specification");

        return new RequestSpecBuilder()
                .setContentType("application/x-www-form-urlencoded")
                .addFilter(new AllureRestAssured())
                .log(LogDetail.ALL)
                .build();
    }

    public static synchronized RequestSpecification getLogSpecification() {
        log.info("Building log specification");

        RequestSpecBuilder logBuilder = new RequestSpecBuilder();
        String loggingEnabled = propertyReader.getProperty("log.enabled", "true");

        if (loggingEnabled.equalsIgnoreCase("true")) {
            logBuilder.addFilter(new AllureRestAssured());
            logBuilder.log(LogDetail.ALL);
        }

        return logBuilder.build();
    }
}