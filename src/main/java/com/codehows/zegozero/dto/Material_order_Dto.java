package com.codehows.zegozero.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Material_order_Dto {

    private int order_id;
    private String product_name;
    private int production_quantity;
    private Date estimated_start_date;

}
