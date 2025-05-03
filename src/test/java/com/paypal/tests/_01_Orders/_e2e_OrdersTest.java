package com.paypal.tests._01_Orders;

import com.paypal.constants.StatusCode;
import com.paypal.pojo.order.OrderRequest;
import com.paypal.tests._000_BaseTest;
import com.paypal.utils.AllureReportUtils;
import com.paypal.utils.AssertionUtils;
import com.paypal.utils.TestDataLoader;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.UUID;

@Epic("PayPal API Testing")
@Feature("Orders API Functionality")
public class _e2e_OrdersTest extends _000_BaseTest {

    private String orderId;

    @Test(priority = 1)
    void Test_01_CreateOrder() {

        OrderRequest orderRequest = TestDataLoader.loadData("orders/createOrder.json", OrderRequest.class);
        AllureReportUtils.logStep("Creating order with loaded test data");
        Response response = requestFactory.createOrder(orderRequest);
        AssertionUtils.verifyStatusCode(response, StatusCode.CREATED.getCode());
        orderId = AssertionUtils.verifyAndGetResponseValue(response, "id", String.class);
        log.info("Order ID: {}", orderId);
        AssertionUtils.verifyStringValue(response, "status", "CREATED");
        AllureReportUtils.logStep("Order created successfully with ID: " + orderId);

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
