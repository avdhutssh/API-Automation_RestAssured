package com.paypal.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtils {
    private static final Logger LOGGER = LogManager.getLogger(LogUtils.class);

    public static void info(String message) {
        LOGGER.info("[Thread: {}] - {}", Thread.currentThread().getId(), message);
    }

    public static void error(String message, Throwable throwable) {
        LOGGER.error("[Thread: {}] - {}", Thread.currentThread().getId(), message, throwable);
    }

    public static void debug(String message) {
        LOGGER.debug("[Thread: {}] - {}", Thread.currentThread().getId(), message);
    }
}