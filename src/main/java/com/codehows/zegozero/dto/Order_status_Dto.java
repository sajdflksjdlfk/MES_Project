package com.codehows.zegozero.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order_status_Dto {

    private int order_id;
    private String equipment_name;
    private Date start_date;
    private Date end_date;
    private String customer_name;
    private Date shipping_date;

}
