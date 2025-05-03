package com.paypal.utils;

import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class CommonUtils {
    private static final Logger log = LogManager.getLogger(CommonUtils.class);

    private CommonUtils() {
    }

    public static String generateUniqueId() {
        return UUID.randomUUID().toString();
    }

    public static String generateFutureExpiryDate(int monthsInFuture) {
        LocalDate futureDate = LocalDate.now().plusMonths(monthsInFuture);
        return futureDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));
    }

    public static <T> T getJsonPayloadValue(Response response, String path) {
        T value = response.jsonPath().get(path);
        return value;
    }
}