package com.codehows.zegozero.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Material_order_management_Dto {

    private int order_id;
    private int purchase_matarial_id;
    private String raw_material;
    private int order_quantity;
    private Date order_date;
    private Date delivery_completion_date;
    private String delivery_status;

}
