package com.student.tests;

import com.student.util.PropertyReader;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    public static PropertyReader prop;

    @BeforeAll
    public static void init() {
        prop = PropertyReader.getInstance();

        RestAssured.baseURI = prop.getProperty("baseUrl");
        RestAssured.port = Integer.valueOf(prop.getProperty("port"));
    }
}
