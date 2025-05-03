package com.paypal.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestDataLoader {
    private static final Logger log = LogManager.getLogger(TestDataLoader.class);
    private static final ObjectMapper objectMapper = configureObjectMapper();

    private static final String TEST_DATA_PATH = "src/test/resources/testdata/";

    private static ObjectMapper configureObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        // Configure ObjectMapper for common settings
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        return mapper;
    }

    public static <T> T loadData(String fileName, Class<T> classname) {
        log.info("Loading test data from file: {} as class: {}", fileName, classname.getSimpleName());
        try {
            return objectMapper.readValue(new File(TEST_DATA_PATH + fileName), classname);
        } catch (IOException e) {
            log.error("Failed to load test data: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to load test data: " + e.getMessage(), e);
        }
    }

    public static String loadJsonAsString(String fileName) {
        log.info("Loading JSON data as string from file: {}", fileName);
        try {
            return new String(Files.readAllBytes(Paths.get(TEST_DATA_PATH + fileName)));
        } catch (IOException e) {
            log.error("Failed to load JSON as string: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to load JSON as string: " + e.getMessage(), e);
        }
    }

    public static <T> String convertToJson(T object) {
        log.info("Converting object to JSON: {}", object.getClass().getSimpleName());
        try {
            return objectMapper.writeValueAsString(object);
        } catch (IOException e) {
            log.error("Failed to convert object to JSON: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to convert object to JSON: " + e.getMessage(), e);
        }
    }

    public static <T> T convertFromJson(String json, Class<T> classname) {
        log.info("Converting JSON to object of type: {}", classname.getSimpleName());
        try {
            return objectMapper.readValue(json, classname);
        } catch (IOException e) {
            log.error("Failed to convert JSON to object: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to convert JSON to object: " + e.getMessage(), e);
        }
    }
}