package com.paypal.pojo.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponse {
    private String id;

    private String status;

    private String intent;

    @JsonProperty("purchase_units")
    private List<PurchaseUnit> purchaseUnits;

    @JsonProperty("create_time")
    private String createTime;

    @JsonProperty("payment_source")
    private PaymentSource paymentSource;
    
    private List<Link> links;
}