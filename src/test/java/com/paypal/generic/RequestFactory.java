package com.paypal.generic;

import com.paypal.constants.Endpoints;
import com.paypal.pojo.order.OrderRequest;
import com.paypal.tests._000_BaseTest;
import com.paypal.utils.AllureReportUtils;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RequestFactory extends _000_BaseTest {
    private static final Logger log = LogManager.getLogger(RequestFactory.class);
    private RestClient restClient = new RestClient();

    @Step("Create new order")
    public Response createOrder(OrderRequest orderRequest) {
        log.info("Creating new order");
        AllureReportUtils.logStep("Creating new order");

        return restClient.doPostRequest(Endpoints.CREATE_ORDER, orderRequest, accessToken);
    }

    @Step("Get order details for order ID: {orderId}")
    public Response getOrderDetails(String orderId) {
        log.info("Getting order details for ID: {}", orderId);
        AllureReportUtils.logStep("Getting order details for ID: " + orderId);

        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put("orderId", orderId);

        return restClient.doGetRequest(Endpoints.GET_ORDER_DETAILS, pathParams, accessToken);
    }

    @Step("Update order with ID: {orderId}")
    public Response updateOrder(String orderId, Object patchPayload) {
        log.info("Updating order with ID: {}", orderId);
        AllureReportUtils.logStep("Updating order with ID: " + orderId);

        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put("orderId", orderId);

        return restClient.doPatchRequest(Endpoints.UPDATE_ORDER, patchPayload, pathParams, accessToken);
    }

    @Step("Confirm order with ID: {orderId}")
    public Response confirmOrder(String orderId, Object confirmPayload) {
        log.info("Confirming order with ID: {}", orderId);
        AllureReportUtils.logStep("Confirming order with ID: " + orderId);

        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put("orderId", orderId);

        return restClient.doPostRequest(Endpoints.CONFIRM_ORDER, confirmPayload, pathParams, accessToken);
    }

    @Step("Authorize order with ID: {orderId}")
    public Response authorizeOrder(String orderId) {
        log.info("Authorizing order with ID: {}", orderId);
        AllureReportUtils.logStep("Authorizing order with ID: " + orderId);

        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put("orderId", orderId);

        String requestId = UUID.randomUUID().toString();
        log.info("Generated request ID: {}", requestId);

        // You can add logic to include request-id in headers if needed
        return restClient.doPostRequest(Endpoints.AUTHORIZE_ORDER, "{}", pathParams, accessToken);
    }

    @Step("Capture order with ID: {orderId}")
    public Response captureOrder(String orderId) {
        log.info("Capturing order with ID: {}", orderId);
        AllureReportUtils.logStep("Capturing order with ID: " + orderId);

        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put("orderId", orderId);

        String requestId = UUID.randomUUID().toString();
        log.info("Generated request ID: {}", requestId);

        // You can add logic to include request-id in headers if needed
        return restClient.doPostRequest(Endpoints.CAPTURE_ORDER, "{}", pathParams, accessToken);
    }
}