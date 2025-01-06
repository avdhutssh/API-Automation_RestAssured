package com.exercise;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestDemo {
    String jsonArray = "[{\"color\":\"red\",\"value\":\"#f00\"},{\"color\":\"green\",\"value\":\"#0f0\"},{\"color\":\"blue\",\"value\":\"#00f\"}," +
            "{\"color\":\"cyan\",\"value\":\"#0ff\"},{\"color\":\"magenta\",\"value\":\"#f0f\"},{\"color\":\"yellow\",\"value\":\"#ff0\"},{\"color\":\"black\",\"value\":\"#000\"}]";

    @Test
    public void extractColorValue() {
        String colorToFind = "magenta";
        String colorValue = JsonPath
                .from(jsonArray)
                .getString("findAll { it.color ==~ /(?i)" + colorToFind + "/ }.value[0]");

        System.out.println("The value for color " + colorToFind + " is: " + colorValue);
        Assert.assertEquals(colorValue, "#f0f", "Value does not match");
    }

    @Test
    public void extractValue() {
        String color = "magenta";
        String jsonArray = "[{\"color\":\"red\",\"value\":\"#f00\"},{\"color\":\"green\",\"value\":\"#0f0\"},{\"color\":\"blue\",\"value\":\"#00f\"},{\"color\":\"cyan\",\"value\":\"#0ff\"},{\"color\":\"magenta\",\"value\":\"#f0f\"},{\"color\":\"yellow\",\"value\":\"#ff0\"},{\"color\":\"black\",\"value\":\"#000\"}]";
        JsonPath js = new JsonPath(jsonArray);
        String colorValue = js.get("[?(@.color=='magenta')].value");
        System.out.println(colorValue);
    }
}
