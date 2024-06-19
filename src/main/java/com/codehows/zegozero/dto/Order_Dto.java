package com.codehows.zegozero.dto;

import com.codehows.zegozero.entity.Orders;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order_Dto {

    private int order_id;

    @NotNull(message = "Product name cannot be null")
    @Size(min = 1, message = "Product name cannot be empty")
    private String product_name;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity cannot be negative")
    private int quantity;

    private int used_inventory;

    private int production_quantity;

    private Date order_date;

    private Date expected_shipping_date;

    @NotNull(message = "Customer name cannot be null")
    @Size(min = 1, message = "Customer name cannot be empty")
    private String customer_name;

    @NotNull(message = "Delivery address cannot be null")
    @Size(min = 1, message = "Delivery address cannot be empty")
    private String delivery_address;

    private Date shipping_date;

    private Boolean deletable;

    public Order_Dto(Orders orders) {

        this.order_id = orders.getOrder_id();
        this.product_name = orders.getProduct_name();
        this.quantity = orders.getQuantity();
        this.used_inventory = orders.getUsed_inventory();
        this.production_quantity = orders.getProduction_quantity();
        this.order_date = orders.getOrder_date();
        this.expected_shipping_date = orders.getExpected_shipping_date();
        this.customer_name = orders.getCustomer_name();
        this.delivery_address = orders.getDelivery_address();
        this.deletable = orders.getDeletable();
        this.shipping_date = orders.getShipping_date();
    }
}
