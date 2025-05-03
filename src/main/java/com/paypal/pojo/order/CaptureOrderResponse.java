package com.paypal.pojo.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CaptureOrderResponse {
    private String id;

    private String status;

    @JsonProperty("purchase_units")
    private List<PurchaseUnit> purchaseUnits;

    private Payer payer;

    private List<Link> links;
}