package com.codehows.zegozero.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order_Dto {

    private int order_id;
    private String product_name;
    private int quantity;
    private int used_inventory;
    private int production_quantity;
    private Date order_date;
    private Date expected_shipping_date;
    private String customer_name;
    private String delivery_address;
    private Date shipping_date;
}
