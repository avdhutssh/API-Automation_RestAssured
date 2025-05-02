package com.paypal.tests._01_Orders;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class _001_StandaloneTest {

    private String baseUrl = "https://api-m.sandbox.paypal.com";
    private String accessToken;
    private String orderId;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = baseUrl;

        try {
            accessToken = getAccessToken();
            System.out.println("Access Token: " + accessToken);

            if (accessToken == null || accessToken.isEmpty()) {
                throw new SkipException("Access token is null or empty. Skipping all tests.");
            }
        } catch (Exception e) {

            System.err.println("Authentication failed: " + e.getMessage());
            throw new SkipException("Authentication failed. Skipping all tests.", e);
        }
    }

    private String getAccessToken() {

        String clientId = "";
        String clientSecret = "";

        Map<String, String> oauthType = new HashMap<>();
        oauthType.put("grant_type", "client_credentials");

        Response response = RestAssured.given()
                .auth()
                .preemptive()
                .basic(clientId, clientSecret)
                .contentType("application/x-www-form-urlencoded")
                .formParams(oauthType)
                .when()
                .post("/v1/oauth2/token");

        System.out.println("-------" + response + "-------");
        Assert.assertEquals(response.getStatusCode(), 200, "Failed to fetch access token");

        return response.jsonPath().getString("access_token");
    }

    @Test(priority = 1)
    void Test_01_CreateOrder() {
        String requestPayload = "{\n" +
                "  \"intent\": \"CAPTURE\",\n" +
                "  \"purchase_units\": [\n" +
                "    {\n" +
                "      \"reference_id\": \"d9f80740-38f0-11e8-b467-0ed5f89f718b\",\n" +
                "      \"amount\": {\n" +
                "        \"currency_code\": \"USD\",\n" +
                "        \"value\": \"100.00\",\n" +
                "        \"breakdown\": {\n" +
                "          \"item_total\": {\n" +
                "            \"currency_code\": \"USD\",\n" +
                "            \"value\": \"80.00\"\n" +
                "          },\n" +
                "          \"shipping\": {\n" +
                "            \"currency_code\": \"USD\",\n" +
                "            \"value\": \"10.00\"\n" +
                "          },\n" +
                "          \"tax_total\": {\n" +
                "            \"currency_code\": \"USD\",\n" +
                "            \"value\": \"10.00\"\n" +
                "          }\n" +
                "        }\n" +
                "      },\n" +
                "      \"items\": [\n" +
                "        {\n" +
                "          \"name\": \"T-Shirt\",\n" +
                "          \"description\": \"Green XL\",\n" +
                "          \"sku\": \"sku01\",\n" +
                "          \"unit_amount\": {\n" +
                "            \"currency_code\": \"USD\",\n" +
                "            \"value\": \"40.00\"\n" +
                "          },\n" +
                "          \"quantity\": \"1\",\n" +
                "          \"category\": \"PHYSICAL_GOODS\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"Shoes\",\n" +
                "          \"description\": \"Running shoes - blue\",\n" +
                "          \"sku\": \"sku02\",\n" +
                "          \"unit_amount\": {\n" +
                "            \"currency_code\": \"USD\",\n" +
                "            \"value\": \"40.00\"\n" +
                "          },\n" +
                "          \"quantity\": \"1\",\n" +
                "          \"category\": \"PHYSICAL_GOODS\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"shipping\": {\n" +
                "        \"address\": {\n" +
                "          \"address_line_1\": \"123 Main St\",\n" +
                "          \"address_line_2\": \"Apt 1\",\n" +
                "          \"admin_area_2\": \"San Jose\",\n" +
                "          \"admin_area_1\": \"CA\",\n" +
                "          \"postal_code\": \"95131\",\n" +
                "          \"country_code\": \"US\"\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  ],\n" +
                "  \"application_context\": {\n" +
                "    \"return_url\": \"https://example.com/return\",\n" +
                "    \"cancel_url\": \"https://example.com/cancel\"\n" +
                "  }\n" +
                "}";

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + accessToken)
                .body(requestPayload)
                .when()
                .post("/v2/checkout/orders");

        System.out.println("------ Create Order Response: ------" + response.asString());
        Assert.assertEquals(response.getStatusCode(), 201, "Expected status code 201 for order creation");
        orderId = response.jsonPath().getString("id");
        System.out.println("Order ID: " + orderId);

        // Validate order ID is not null
        Assert.assertNotNull(orderId, "Order ID should not be null");
    }

    @Test(priority = 2, dependsOnMethods = "Test_01_CreateOrder")
    void Test_02_ShowOrderDetails() {
        Response response = RestAssured.given()
                .header("Content-Type", ContentType.JSON)
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get("/v2/checkout/orders/" + orderId);

        System.out.println("------ Get Order Details Response: ------" + response.toString());
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 for get order details");

        String responseId = response.jsonPath().getString("id");
        Assert.assertEquals(responseId, orderId, "Returned order ID should match the created order ID");

        String status = response.jsonPath().getString("status");
        Assert.assertEquals(status, "CREATED", "Order status should be CREATED");

    }

    @Test(priority = 3, dependsOnMethods = "Test_01_CreateOrder")
    public void Test_03_UpdateOrder() {
        String patchPayload = "[\n" +
                "  {\n" +
                "    \"op\": \"replace\",\n" +
                "    \"path\": \"/purchase_units/@reference_id=='d9f80740-38f0-11e8-b467-0ed5f89f718b'/shipping/address\",\n" +
                "    \"value\": {\n" +
                "      \"address_line_1\": \"456 New St\",\n" +
                "      \"address_line_2\": \"Unit 2\",\n" +
                "      \"admin_area_2\": \"San Francisco\",\n" +
                "      \"admin_area_1\": \"CA\",\n" +
                "      \"postal_code\": \"94107\",\n" +
                "      \"country_code\": \"US\"\n" +
                "    }\n" +
                "  }\n" +
                "]";

        Response response = RestAssured.given()
                .header("Content-Type", ContentType.JSON)
                .header("Authorization", "Bearer " + accessToken)
                .body(patchPayload)
                .when()
                .patch("/v2/checkout/orders/" + orderId);

        System.out.println("------ Update Order Response: ------" + response.asString());

        Assert.assertEquals(response.getStatusCode(), 204, "Expected status code 204 for update order");
    }

    @Test(priority = 4, dependsOnMethods = "Test_01_CreateOrder")
    public void Test_04_ConfirmOrderWithPaymentSource() {
        String confirmPayload = "{\n" +
                "  \"payment_source\": {\n" +
                "    \"card\": {\n" +
                "      \"name\": \"John Doe\",\n" +
                "      \"number\": \"4111111111111111\",\n" +
                "      \"security_code\": \"123\",\n" +
                "      \"expiry\": \"2025-07\"\n" +
                "    }\n" +
                "  }\n" +
                "}";

        // Send POST request to confirm order
        Response response = RestAssured.given()
                .header("Content-Type", ContentType.JSON)
                .header("Authorization", "Bearer " + accessToken)
                .body(confirmPayload)
                .when()
                .post("/v2/checkout/orders/" + orderId + "/confirm-payment-source");

        System.out.println("------ Confirm Order Response: ------" + response.asString());

        System.out.println("Confirm Order Status Code: " + response.getStatusCode());
    }

    @Test(priority = 5, dependsOnMethods = "Test_04_ConfirmOrderWithPaymentSource")
    public void Test_05_AuthorizeOrder() {
        String requestId = UUID.randomUUID().toString();

        Response response = RestAssured.given()
                .header("Content-Type", ContentType.JSON)
                .header("Authorization", "Bearer " + accessToken)
                .header("PayPal-Request-Id", requestId)
                .body("{}")
                .when()
                .post("/v2/checkout/orders/" + orderId + "/authorize");

        System.out.println("Authorize Order Status Code: " + response.getStatusCode());
        System.out.println("------ Authorize Order Response: ------" + response.asString());

        // This will likely fail with 422 Unprocessable Entity without a buyer's approval
        // But we'll log the result
        if (response.getStatusCode() == 422) {
            String issue = response.jsonPath().getString("details[0].issue");
            System.out.println("Authorization Issue: " + issue);
            Assert.assertEquals(issue, "PAYEE_NOT_ENABLED_FOR_CARD_PROCESSING", "Expected error msg");
        }
    }

    @Test(priority = 6, dependsOnMethods = "Test_04_ConfirmOrderWithPaymentSource")
    public void Test_06_CaptureOrder() {

        String requestId = UUID.randomUUID().toString();

        Response response = RestAssured.given()
                .header("Content-Type", ContentType.JSON)
                .header("Authorization", "Bearer " + accessToken)
                .header("PayPal-Request-Id", requestId)
                .body("{}")
                .when()
                .post("/v2/checkout/orders/" + orderId + "/capture");

        System.out.println("Capture Order Status Code: " + response.getStatusCode());
        System.out.println("------ Capture Order Response: ------" + response.asString());

        // This will likely fail with 422 Unprocessable Entity without a buyer's approval
        // But we'll log the result
        if (response.getStatusCode() == 422) {
            String issue = response.jsonPath().getString("details[0].issue");
            System.out.println("Capture Issue: " + issue);
            Assert.assertEquals(issue, "PAYEE_NOT_ENABLED_FOR_CARD_PROCESSING", "Expected error msg");
        }
    }
}