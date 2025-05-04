package com.paypal.tests;

import com.paypal.constants.Endpoints;
import com.paypal.generic.RequestFactory;
import com.paypal.pojo.auth.TokenResponse;
import com.paypal.utils.AllureReportUtils;
import com.paypal.utils.PropertyReader;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.SkipException;
import org.testng.annotations.BeforeSuite;

import java.util.HashMap;
import java.util.Map;

public class _000_BaseTest {

    protected static final Logger log = LogManager.getLogger(_000_BaseTest.class);
    private static final PropertyReader propertyReader = PropertyReader.getInstance();
    protected static String baseUrl;
    protected static String accessToken;
    protected RequestFactory requestFactory;

    @BeforeSuite
    public void setup() {
        baseUrl = System.getProperty("paypal.base.url");
        if (baseUrl == null || baseUrl.isEmpty()) {
            baseUrl = propertyReader.getProperty("paypal.base.url", "https://api-m.sandbox.paypal.com");
        }
        RestAssured.baseURI = baseUrl;
        log.info("Base URI set to: {}", RestAssured.baseURI);

        RestAssured.filters(
                new RequestLoggingFilter(),
                new ResponseLoggingFilter()
        );

        try {
            accessToken = getAccessToken();
            log.info("Access Token: {}", accessToken.substring(0, 10) + "...");
            if (accessToken == null || accessToken.isEmpty()) {
                log.error("Access token is null or empty");
                throw new SkipException("Access token is null or empty. Skipping all tests.");
            }
            requestFactory = new RequestFactory();
            AllureReportUtils.logStep("Test environment initialized with base URL: " + RestAssured.baseURI);
        } catch (Exception e) {
            log.error("Authentication failed: {}", e.getMessage(), e);
            throw new SkipException("Authentication failed. Skipping all tests.", e);
        }
    }

    private String getAccessToken() {
        log.info("Fetching access token");

        String clientId = null;
        String clientSecret = null;

        clientId = System.getProperty("client.id");
        clientSecret = System.getProperty("client.secret");

        if (clientId != null && clientSecret != null) {
            log.info("Using credentials from command line arguments");
        } else {
            clientId = System.getenv("CLIENT_ID");
            clientSecret = System.getenv("CLIENT_SECRET");

            if (clientId != null && clientSecret != null) {
                log.info("Using credentials from environment variables");
            } else {
                clientId = propertyReader.getProperty("client.id");
                clientSecret = propertyReader.getProperty("client.secret");

                if (clientId != null && clientSecret != null) {
                    log.info("Using credentials from application.properties");
                } else {
                    log.error("No credentials found in any source");
                    throw new RuntimeException("Authentication credentials not found in any source");
                }
            }
        }
        Map<String, String> formParams = new HashMap<>();
        formParams.put("grant_type", "client_credentials");

        Response response = RestAssured.given()
                .auth()
                .preemptive()
                .basic(clientId, clientSecret)
                .contentType("application/x-www-form-urlencoded")
                .formParams(formParams)
                .when()
                .post(Endpoints.OAUTH_TOKEN);

        log.info("Token response status code: {}", response.getStatusCode());

        TokenResponse tokenResponse = response.as(TokenResponse.class);
        if (response.getStatusCode() != 200) {
            log.error("Failed to get access token. Status code: {}", response.getStatusCode());
            log.error("Response body: {}", response.getBody().asString());
            throw new RuntimeException("Failed to get access token. Status code: " + response.getStatusCode());
        }

        return tokenResponse.getAccessToken();
    }
}
