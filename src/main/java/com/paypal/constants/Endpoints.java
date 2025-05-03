package com.paypal.constants;

public class Endpoints {
    // Auth endpoints
    public static final String OAUTH_TOKEN = "/v1/oauth2/token";

    // Orders endpoints
    public static final String CREATE_ORDER = "/v2/checkout/orders";
    public static final String GET_ORDER_DETAILS = "/v2/checkout/orders/{orderId}";
    public static final String UPDATE_ORDER = "/v2/checkout/orders/{orderId}";
    public static final String CONFIRM_ORDER = "/v2/checkout/orders/{orderId}/confirm-payment-source";
    public static final String AUTHORIZE_ORDER = "/v2/checkout/orders/{orderId}/authorize";
    public static final String CAPTURE_ORDER = "/v2/checkout/orders/{orderId}/capture";
}