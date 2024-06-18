package com.codehows.zegozero.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Shipment_management_dto {
    private Integer order_id;
    private String product_name;
    private Integer quantity;
    private String customer_name;
    private String delivery_address;
    private Date expected_shipping_date;
    private Date shipping_date;
}
