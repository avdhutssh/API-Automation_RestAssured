package com.paypal.pojo.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Payer {
    @JsonProperty("name")
    private PayerName name;

    @JsonProperty("email_address")
    private String emailAddress;

    @JsonProperty("payer_id")
    private String payerId;

    private Address address;
}