package com.paypal.tests;

import com.paypal.constants.StatusCode;
import com.paypal.pojo.order.*;
import com.paypal.utils.AllureReportUtils;
import com.paypal.utils.AssertionUtils;
import com.paypal.utils.CommonUtils;
import com.paypal.utils.TestDataLoader;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

@Epic("PayPal API Testing")
@Feature("Orders API Functionality")
public class _e2e_OrdersTest extends _000_BaseTest {

    private String orderId;

    @Test(priority = 1)
    @Story("Create Order")
    @Description("Test to create a new order using PayPal API")
    @Severity(SeverityLevel.CRITICAL)
    public void Test_01_CreateOrder() {
        OrderRequest orderRequest = TestDataLoader.loadData("orders/createOrder.json", OrderRequest.class);
        AllureReportUtils.logStep("Creating order with loaded test data");
        Response response = requestFactory.createOrder(orderRequest);
        AssertionUtils.verifyStatusCode(response, StatusCode.CREATED.getCode());

        OrderResponse orderResponse = response.as(OrderResponse.class);
        orderId = orderResponse.getId();
        log.info("Order ID: {}", orderId);
        AssertionUtils.verifyNotNullField(orderId);
        AssertionUtils.verifyStringValue(response, "status", "CREATED");
        AssertionUtils.verifyCorrectValue(orderResponse.getStatus(), "CREATED");
        AssertionUtils.verifyCorrectValue(orderResponse.getIntent(), "CAPTURE");
        AssertionUtils.verifyCondition(orderResponse.getPurchaseUnits().size() > 0, "Purchase units should be present");
        AssertionUtils.verifyCondition(orderResponse.getLinks().size() > 0, "Links should be present");
        AllureReportUtils.logStep("Order created successfully with ID: " + orderId);
    }

    @Test(priority = 2, dependsOnMethods = "Test_01_CreateOrder")
    @Story("Get Order Details")
    @Description("Test to get order details from PayPal API")
    @Severity(SeverityLevel.NORMAL)
    void Test_02_ShowOrderDetails() {
        AllureReportUtils.logStep("Getting details for order ID: " + orderId);
        Response response = requestFactory.getOrderDetails(orderId);
        AssertionUtils.verifyStatusCode(response, StatusCode.OK.getCode());

        OrderResponse orderResponse = response.as(OrderResponse.class);
        AssertionUtils.verifyCorrectValue(orderResponse.getId(), orderId);
        AssertionUtils.verifyCorrectValue(orderResponse.getStatus(), "CREATED");

        PurchaseUnit purchaseUnit = orderResponse.getPurchaseUnits().get(0);
        AssertionUtils.verifyCorrectValue(purchaseUnit.getAmount().getCurrencyCode(), "USD", "Currency code should be USD");
        AssertionUtils.verifyCorrectValue(purchaseUnit.getAmount().getValue(), "100.00", "Amount value should be 100.00");
        AssertionUtils.verifyCondition(purchaseUnit.getItems().size() == 2, "Should have 2 items");

        Address address = purchaseUnit.getShipping().getAddress();
        AssertionUtils.verifyCorrectValue(address.getAdminArea2(), "Mumbai", "City should be Mumbai");
        AssertionUtils.verifyCorrectValue(address.getPostalCode(), "95131", "Postal code should be 95131");

        AllureReportUtils.logStep("Successfully verified order details");
    }

    @Test(priority = 3, dependsOnMethods = "Test_01_CreateOrder")
    @Story("Update Order")
    @Description("Test to update an existing order using PayPal API")
    @Severity(SeverityLevel.NORMAL)
    void Test_03_UpdateOrder() {
        AllureReportUtils.logStep("Updating order with ID: " + orderId);
        PatchOperation[] patchOperations = TestDataLoader.loadData("orders/updateOrder.json", PatchOperation[].class);
        System.out.println(patchOperations.toString());
        Response response = requestFactory.updateOrder(orderId, patchOperations);
        AssertionUtils.verifyStatusCode(response, StatusCode.NO_CONTENT.getCode());
        AllureReportUtils.logStep("Order successfully updated and verified");
    }

    @Test(priority = 4, dependsOnMethods = "Test_01_CreateOrder")
    @Story("Confirm Order")
    @Description("Test to confirm an order with payment source")
    @Severity(SeverityLevel.CRITICAL)
    void Test_04_ConfirmOrder() {
        AllureReportUtils.logStep("Confirming order with ID: " + orderId);
        ConfirmOrderRequest confirmRequest = TestDataLoader.loadData("orders/confirmOrder.json", ConfirmOrderRequest.class);
        Response response = requestFactory.confirmOrder(orderId, confirmRequest);

        AssertionUtils.verifyStatusCode(response, StatusCode.OK.getCode());
        AssertionUtils.verifyStringValue(response, "status", "APPROVED");
        ConfirmOrderResponse confirmResponse = response.as(ConfirmOrderResponse.class);
        AssertionUtils.verifyCorrectValue(confirmResponse.getStatus(), "APPROVED",
                "Order status should be APPROVED");

        AssertionUtils.verifyCorrectValue(confirmResponse.getPaymentSource().getCard().getName(), "Avdhut Shirgaonkar",
                "Name should be user name's");

        AllureReportUtils.logStep("Order confirmation successful with status: " + confirmResponse.getStatus());
    }

    @Test(priority = 5, dependsOnMethods = "Test_04_ConfirmOrder")
    @Story("Authorize Order")
    @Description("Test to authorize an order")
    @Severity(SeverityLevel.CRITICAL)
    void Test_05_AuthorizeOrder() {
        AllureReportUtils.logStep("Authorizing order with ID: " + orderId);

        Response response = requestFactory.authorizeOrder(orderId);

        log.info("Authorize Order Status Code: {}", response.getStatusCode());

        if (response.getStatusCode() == StatusCode.UNPROCESSABLE_ENTITY.getCode()) {
            String issue = CommonUtils.getJsonPayloadValue(response, "details[0].issue");
            AssertionUtils.verifyNotNullField(issue);
            log.info("Authorization Issue: {}", issue);
            boolean isExpectedError = issue.equals("PAYEE_NOT_ENABLED_FOR_CARD_PROCESSING") ||
                    issue.equals("ORDER_NOT_APPROVED");

            AssertionUtils.verifyCondition(isExpectedError, "Expected known error when authorizing a test order");

            AllureReportUtils.logStep("Received expected authorization error: " + issue);
        } else {
            AssertionUtils.verifyCondition(false, "Unexpected response code: " + response.getStatusCode());
        }
    }

    @Test(priority = 6, dependsOnMethods = "Test_04_ConfirmOrder")
    @Story("Capture Order")
    @Description("Test to capture an order")
    @Severity(SeverityLevel.CRITICAL)
    void Test_06_CaptureOrder() {
        AllureReportUtils.logStep("Capturing order with ID: " + orderId);

        String requestId = CommonUtils.generateUniqueId();
        log.info("Using Request-ID: {}", requestId);

        Response response = requestFactory.captureOrder(orderId);
        log.info("Capture Order Status Code: {}", response.getStatusCode());
        AssertionUtils.verifyStatusCode(response, StatusCode.UNPROCESSABLE_ENTITY.getCode());
        String issue = CommonUtils.getJsonPayloadValue(response, "details[0].issue");
        log.info("Capture Issue: {}", issue);
        AllureReportUtils.logStep("Received expected capture error: " + issue);

    }

    @Test(priority = 7)
    @Story("End-to-End Payment Flow")
    @Description("Test the entire payment flow from order creation to completion")
    @Severity(SeverityLevel.BLOCKER)
    void Test_07_EndToEndPaymentFlow() {
        // This test demonstrates how to create a more complex, high-level test that
        // orchestrates multiple API calls in a single business process

        AllureReportUtils.logStep("Starting end-to-end payment flow test");

        // 1. Create a fresh order for this test
        OrderRequest orderRequest = TestDataLoader.loadData("orders/createOrder.json", OrderRequest.class);
        Response createResponse = requestFactory.createOrder(orderRequest);
        AssertionUtils.verifyStatusCode(createResponse, StatusCode.CREATED.getCode());

        // Extract response using POJO
        OrderResponse orderResponse = createResponse.as(OrderResponse.class);
        String newOrderId = orderResponse.getId();
        AssertionUtils.verifyNotNullField(newOrderId);
        AllureReportUtils.logStep("Created new order with ID: " + newOrderId);

        // 2. Confirm the order with payment details
        ConfirmOrderRequest confirmRequest = TestDataLoader.loadData("orders/confirmOrder.json", ConfirmOrderRequest.class);
        Response confirmResponse = requestFactory.confirmOrder(newOrderId, confirmRequest);
        log.info("Confirm response status: {}", confirmResponse.getStatusCode());

        // 3. Attempt to capture the payment
        // Note: In a sandbox environment, this is expected to fail with a specific error
        Response captureResponse = requestFactory.captureOrder(newOrderId);
        log.info("Capture response status: {}", captureResponse.getStatusCode());

        if (captureResponse.getStatusCode() == StatusCode.UNPROCESSABLE_ENTITY.getCode()) {
            String issue = CommonUtils.getJsonPayloadValue(captureResponse, "details[0].issue");
            log.info("Expected capture issue in E2E flow: {}", issue);
            AllureReportUtils.logStep("Received expected capture validation in E2E flow: " + issue);
        }

        Response finalStatusResponse = requestFactory.getOrderDetails(newOrderId);
        AssertionUtils.verifyStatusCode(finalStatusResponse, StatusCode.OK.getCode());

        OrderResponse finalOrder = finalStatusResponse.as(OrderResponse.class);
        AssertionUtils.verifyNotNullField(finalOrder.getStatus());

        log.info("Final order status: {}", finalOrder.getStatus());
        AllureReportUtils.logStep("End-to-end flow completed with final status: " + finalOrder.getStatus());
    }
}