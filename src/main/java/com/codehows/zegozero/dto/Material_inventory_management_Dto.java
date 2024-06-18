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
public class Material_inventory_management_Dto {
    private Integer order_id;
    private Integer purchase_order_id;
    private String raw_material;
    private Integer received_quantity;
    private Date received_date;
    private Integer shipped_quantity;
    private Date shipped_date;
}
