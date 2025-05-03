package com.paypal.tests._01_Orders;

import com.paypal.constants.StatusCode;
import com.paypal.pojo.order.Address;
import com.paypal.pojo.order.OrderRequest;
import com.paypal.pojo.order.OrderResponse;
import com.paypal.pojo.order.PurchaseUnit;
import com.paypal.tests._000_BaseTest;
import com.paypal.utils.AllureReportUtils;
import com.paypal.utils.AssertionUtils;
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
}
