package com.exercise._04_Json;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class _11_Json_Interview_Questions {
    static String jsonArray = "[{\"color\":\"red\",\"value\":\"#f00\"},{\"color\":\"green\",\"value\":\"#0f0\"},{\"color\":\"blue\",\"value\":\"#00f\"}," +
            "{\"color\":\"cyan\",\"value\":\"#0ff\"},{\"color\":\"magenta\",\"value\":\"#f0f\"},{\"color\":\"yellow\",\"value\":\"#ff0\"},{\"color\":\"black\",\"value\":\"#000\"}]";

    @Test
    public void _01_extractColorValue_Using_RestAssuredJsonPath() {
        String colorToFind = "magenta";
        String colorValue = JsonPath
                .from(jsonArray)
                .getString("findAll { it.color ==~ /(?i)" + colorToFind + "/ }.value[0]");

        System.out.println("The value for color " + colorToFind + " is: " + colorValue);

        assertEquals(colorValue, "#f0f", "Value does not match");
    }

    @Test
    public void _02_extractColorValue_Using_JaywayJsonPath() {
        String colorToFind = "magenta";
        String jsonPath = "$.[?(@.color == 'magenta')].value";
        String colorValue = (String) com.jayway.jsonpath.JsonPath.parse(jsonArray).read(jsonPath, List.class).get(0);
        System.out.println("The value for color " + colorToFind + " is: " + colorValue);

        assertEquals(colorValue, "#f0f", "Value does not match");
    }


    //how to fetch response headers
    //how to send cookies

}
