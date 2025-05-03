package com.paypal.pojo.order;

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
public class Breakdown {
    @JsonProperty("item_total")
    private UnitAmount itemTotal;

    private UnitAmount shipping;

    @JsonProperty("tax_total")
    private UnitAmount taxTotal;
}