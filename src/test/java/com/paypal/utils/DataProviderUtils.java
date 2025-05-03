package com.paypal.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.pojo.order.OrderRequest;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataProviderUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @DataProvider(name = "orderData")
    public static Iterator<Object[]> getOrderData() {
        List<Object[]> testData = new ArrayList<>();

        try {
            // Read test data from JSON files
            File testDataDir = new File("src/test/resources/testdata/orders");
            if (testDataDir.exists() && testDataDir.isDirectory()) {
                for (File file : testDataDir.listFiles()) {
                    if (file.getName().endsWith(".json")) {
                        OrderRequest orderRequest = objectMapper.readValue(file, OrderRequest.class);
                        testData.add(new Object[]{orderRequest, file.getName()});
                    }
                }
            }
        } catch (IOException e) {
            LogUtils.error("Failed to read test data", e);
        }

        return testData.iterator();
    }
}