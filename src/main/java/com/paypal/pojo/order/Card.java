package com.paypal.pojo.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Card {
    private String name;

    private String number;

    @JsonProperty("security_code")
    private String securityCode;

    private String expiry;

    @JsonProperty("last_digits")
    private String lastDigits;

    private String brand;

    private String type;

    @JsonProperty("bin_details")
    private Object binDetails;
}